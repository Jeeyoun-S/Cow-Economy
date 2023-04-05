import pandas as pd

import re

from datetime import datetime
from pytz import timezone

# 시간 측정
import time

import pymysql
from sqlalchemy import create_engine, text
pymysql.install_as_MySQLdb()
import MySQLdb

from pyspark.sql.types import StringType, DoubleType
from pyspark.sql.window import Window

from pyspark.ml.feature import HashingTF, IDF, Tokenizer
from pyspark.ml.feature import Normalizer

from wordcloud import WordCloud
from konlpy.tag import Kkma

# 오늘 날짜 ex) 20230320
today = datetime.today().astimezone(timezone('Asia/Seoul')).strftime("%Y%m%d")
#today = '20230404'

from pyspark.context import SparkContext
from pyspark.sql.session import SparkSession

sc = SparkContext.getOrCreate()
spark = SparkSession(sc)

# ----------- 경로 설정 ----------
# server = "hdfs://cluster.p.ssafy.io:9000" # 서버
hdfs_path = "hdfs://localhost:9000/news/" # hdfs 폴더 저장 경로

daily_news = 'daily-news/'
word_cloud = 'word-cloud/'

stop_word_path = '/home/ubuntu/data/hadoop/stopword.txt'
font_path = '/home/ubuntu/data/hadoop/assets/NotoSansKR-Black.otf'

# 서버
db_connection_str = 'mysql+pymysql://root:ssafy@j8a509.p.ssafy.io:3306/ssafy_cow_db'
# ----------- 경로 설정 끝 ----------

# HDFS 오류로 임시로 DB에서 불러오기
def getTodayNews():
  print("db에서 뉴스 불러오기")
  # 일주일치 자료 가져오기  
  query_str = 'select article_id, article_title, article_regtime from article where article_regtime >= date(now() - INTERVAL 6 DAY)'
  
  # DB
  db_connection = create_engine(db_connection_str)
  conn = db_connection.connect()
  
  result = pd.read_sql_query(text(query_str), conn)
  print("DB에서 일주일치 데이터 가져옴")
  
  # conn.close()
  
  week_df = spark.createDataFrame(result)
  print('스파크로 변환 완료 ')
  
  # 오늘 날짜 데이터 가져오기
  from pyspark.sql.functions import date_format
  today_df = week_df.filter(date_format('article_regtime', 'yyyyMMdd') == today).select('article_id', 'article_title')
  # 임시 처리용 데이터
  # today_df = week_df.filter(date_format('article_regtime', 'yyyyMMdd') == '20230401').select('article_id', 'article_title')
  
  print("오늘 데이터 분리 완료")
  # print("test >> ", week_df.take(1), today_df.take(1))
  
  return week_df, today_df

# 제거 함수 정의
def remove_unnecessary_words(text, stop_words):
  # <> [] 태그 내 단어, 따옴표, 한영숫자 제외한 기호 제거
  pattern = '<.+?>|\[.+?\]|\d+[a-zA-Z가-힣]+|\d|[\'\"\‘\’\“\”,/·=]|[^a-zA-Z가-힣]'
  words = re.sub(pattern, ' ', text).split()
  
  filtered_words = [word for word in words if word.lower() not in stop_words]
  return ' '.join(filtered_words)

# 뉴스 기사에서 불용어 제거하기 (article_id, text)
def removeStopWords(df, path_str):
  print("### 불용어 제거 ###")

  # 타이틀만 선택. 타이틀을 'text'로 변환 
  from pyspark.sql.functions import col
  text_df = df.select('article_id', col('article_title').alias('text'))
  
  # text 컬럼에서 정규표현식 사용
  
  print("불용어 설정")
  with open(path_str, 'r') as f:
    stop_words = [line.strip() for line in f.readlines()]
  print("불용어 설정 완료")
  
  # 태그와 불필요한 문자 제거, 불용어 제거
  print("불용어 제거 시작")
  start = time.time() # 시작 시간
  from pyspark.sql.functions import udf
  remove_words_udf = udf(lambda x: remove_unnecessary_words(x, stop_words), StringType())
  text_df = text_df.withColumn('text', remove_words_udf('text'))
  print(f">> 불용어 제거 소요 시간 : {time.time() - start:.5f} 초")
  
  return text_df

def readRelatedArticleIdFromDB():
  # 관련 기사 마지막 인덱스 불러오기
  query_str = 'select article_id from related_article order by article_id desc limit 1;'
  
  # DB
  db_connection = create_engine(db_connection_str)
  conn = db_connection.connect()
  
  last_id_df = pd.read_sql_query(text(query_str), conn)
  last_id = 0
  if last_id_df['article_id'].count() != 0:
    last_id = last_id_df['article_id'][0]
  
  # conn.close()
  
  print("DB에서 관련기사 마지막 인덱스 가져옴 >> ", last_id)
  
  return str(last_id)

# 방금 크롤링한 자료, 일주일치 전체뉴스로 분리
def getNowCrawlNews(df, idx):
  print("### 방금 크롤링한 자료 분리 ###")
  # 아이디 기준 정렬
  df = df.sort('article_id')
  
  nowDay = df.filter(df.article_id > idx)

  print("### 방금 크롤링한 자료 분리 완료 ###")
  
  return nowDay, df

# 각 TF-IDF를 구하는 함수. 데이터를 반환
def getTFIDF(df):
  # TF-IDF
  print("### TF-IDF 계산 ###")
  
  if df.count() == 0:
    print("데이터가 존재하지 않습니다.")
    return
  
  start = time.time() # 시작 시간
  
  tokenizer = Tokenizer(inputCol='text', outputCol='words')
  wordsData = tokenizer.transform(df)
  
  hashingTF = HashingTF(inputCol="words", outputCol="tf")
  tf = hashingTF.transform(wordsData)
  
  idf = IDF(inputCol="tf", outputCol="feature").fit(tf)
  tfidf = idf.transform(tf)
  # L2 norm
  
  normalizer = Normalizer(inputCol="feature", outputCol="norm")
  data = normalizer.transform(tfidf)
  print(f">> TF-IDF 소요 시간 : {time.time() - start:.5f} 초")

  return data

# 유사도 검증
def getCosineSimilarity(nowData, totalData):
  print("### 코사인 유사도 계산 ###")
  
  if nowData is None or totalData is None:
    print("데이터가 존재하지 않습니다.")
    return
  
  start = time.time() # 시작 시간
  import pyspark.sql.functions as F

  dot_udf = F.udf(lambda x,y: float(x.dot(y)), DoubleType())
  dot_df = nowData.alias("i").join(totalData.alias("j"), F.col("i.article_id") != F.col("j.article_id"))\
      .select(
          F.col("i.article_id").alias("i"), 
          F.col("j.article_id").alias("j"), 
          dot_udf("i.norm", "j.norm").alias("dot"))
  
  window = Window.partitionBy("i").orderBy(F.desc("dot"))
  ranked_df = dot_df.select("*", F.row_number().over(window).alias("rank")).filter("rank <= 6")
  
  # 가장 유사도가 높은 article_id 6개를 리스트로 반환 (article_id, sub_article_id[])
  result_df = ranked_df.groupBy("i").agg(F.collect_list("j").alias("sub_article_id"))
  result_df = result_df.select(F.col("i").alias("article_id"), F.col("sub_article_id"))

  print(f">> 코사인 유사도 소요 시간 : {time.time() - start:.5f} 초")
  
  return result_df

def saveToDB(df):
  print("### 관련 기사 DB 저장 ###")
  
  if df is None:
    print("저장할 관련 기사가 존재하지 않습니다.")
    return
  
  # sub_article_id 리스트를 ','를 구분자로 하는 문자열로 변환
  
  start = time.time()
  print("아이디 리스트 >> 문자열 변환")
  from pyspark.sql.functions import concat_ws
  df_str = df.withColumn("sub_article_id", concat_ws(",", "sub_article_id"))
  
  print("관련 기사 아이디 >> Pandas 변환")
  pdDf = df_str.toPandas()
  
  print("관련 기사 DB 저장")
  # print("임시 데이터 >> ", pdDf.head())
  # DB로 저장
  db_connection = create_engine(db_connection_str)
  pdDf.to_sql(name='related_article', con=db_connection, if_exists='append',index=False)  

  
  print(f">> 관련 기사 RDD 변환 및 DB 저장 소요 시간 : {time.time() - start:.5f} 초")
  print("### 관련 기사 DB 저장 완료 ###")
  
  return 0

def extract_nouns(sentence):
  # 공백 구분자
  nouns = sentence.split(" ")
  # 1글자 이상인 것만 반환  
  return [noun for noun in nouns if len(noun) > 1]

# 뉴스 파일 명사 추출
def getNounsByOneNews(data):
  print("### 오늘 뉴스 명사 추출 ###")
  
  one_rdd = data.select('text').rdd.flatMap(lambda x: x) # rdd로 만들기
  one_rdd = one_rdd.repartition(4)

  start = time.time() # 시작 시간
  
  # 명사를 추출하고 한 행에 대해서 길이가 1 이상인 rdd만 반환
  nouns_rdd = one_rdd.map(lambda x: extract_nouns(x)).filter(lambda x: len(x) > 0)
  flat_rdd = nouns_rdd.flatMap(lambda x: x)
  
  print(f">> 명사 추출 소요 시간 : {time.time() - start:.5f} 초")
  
  return flat_rdd

# word counting & word cloud
def setWordCloud(data, path_str):
  print("### Word Counting & Word Cloud 생성 ###")
  # 오늘 날짜 데이터가 비었다면 그냥 반환해줌
  if data.count() == 0:
    print("데이터가 존재하지 않습니다.")
    return 'null'
  
  # wordcloud
  start = time.time() # 시작 시간
  
  data2 = data.map(lambda x:(x,1)).groupByKey().mapValues(sum).collect()
  print(f">> 1차 word counting 소요 시간 : {time.time() - start:.5f} 초")
  # print(data2.take(1))
  
  # 명사만 추출
  kkma = Kkma()
  
  start = time.time()
  
  res = []
  for word in data2:
    temp = kkma.nouns(word[0])
    # 빈 리스트는 반환하지 않음
    if len(temp) < 1:
      continue
    # 가장 길이가 긴 단어 추출
    long_word = max(temp, key=len)
    if len(long_word) > 1: # 이 단어의 길이가 1보다 긴 것만 반환
      res.append((long_word, word[1]))
  
  # 명사만 추출한 리스트를 다시 rdd로 변환하고 word counting
  # rdd로 변환
  rdd = sc.parallelize(res)
  
  total_counting = rdd.reduceByKey(lambda x, y: x+y).sortBy(lambda x: x[1], ascending=False).take(100) # 내림차순 정렬
  
  print(f">> 2차 word counting 소요 시간 : {time.time() - start:.5f} 초")
  #print(total_counting)
  # hdfs에 일자별 저장, DB에 overwrite로 저장
  
  # DB에 저장
  print("word cloud db에 저장합니다")
  pdDf = pd.DataFrame(total_counting, columns=['name','value'])
  
  db_connection = create_engine(db_connection_str)
  pdDf.to_sql(name='word_cloud', con=db_connection, if_exists='replace', index=False)
  print("word cloud DB 저장 완료")    
  
  # hdfs에 날짜 폴더로 저장
  save_df = spark.createDataFrame(total_counting, ['name','value'])
  
  save_df.repartition(1).write.mode("append").csv(path_str+today)
  
  print("word counting hdfs 저장 완료")
  
  # word cloud 이미지 생성   
  # rdd -> dict 설정
  
  #data2_dict = data2.sortBy(lambda x: x[1], ascending=False).collectAsMap()
  data2_dict = dict(total_counting)
  # word cloud 설정
  wc = WordCloud(font_path=font_path,
                 background_color='white',
                 height=600,
                 width=1000,
                 max_words=100,
                 max_font_size=200,
                 colormap='Set2')
  cloud = wc.generate_from_frequencies(data2_dict)
  
  # 파일 저장
  cloud.to_file('/home/ubuntu/coweconomy_store/wordcloud/word_cloud.png')

  
  print("#### word cloud 완료 ####")
  
  return 'success'


# ----------------main---------------------

# 뉴스 기사 DB에서 불러오기
week_total, today_total = getTodayNews()

# 일주일 뉴스 기사에서 불용어 제거하기
week_total = removeStopWords(week_total, stop_word_path)

# 마지막 관련기사 아이디 불러오기
last_idx = readRelatedArticleIdFromDB()

# 방금 크롤링한 자료, 일주일치 전체뉴스로 분리
nowDf, week_total = getNowCrawlNews(week_total, last_idx)

# ---------- 관련 기사 ----------
# 방금 크롤링, 전체 뉴스 TF-IDF
nowTfidf = getTFIDF(nowDf)
totalTfidf = getTFIDF(week_total)

# 방금 크롤링에 대해서 전체 유사도 검사
related_df = getCosineSimilarity(nowTfidf, totalTfidf)

# DB에 저장
saveToDB(related_df)

# ---------- word cloud ----------
# 오늘 뉴스 불용어 제거
today_total = removeStopWords(today_total, stop_word_path)
# today_total.show()

# 오늘 뉴스 명사 추출
data_nouns = getNounsByOneNews(today_total)

# word cloud 생성, hdfs에 word cloud dict 적재
end_str = setWordCloud(data_nouns, hdfs_path + word_cloud)
