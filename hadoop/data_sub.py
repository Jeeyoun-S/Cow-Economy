from tqdm import tqdm
import pandas as pd

from datetime import datetime, timedelta
from pytz import timezone

# 시간 측정
import time

import pymysql
from sqlalchemy import create_engine, text
pymysql.install_as_MySQLdb()
import MySQLdb

# 오늘 날짜 ex) 20230320
today = datetime.today().astimezone(timezone('Asia/Seoul')).strftime("%Y%m%d")

# HDFS 오류로 임시로 DB에서 불러오기
def getTodayNews(db_connection_str, spark):
  print("db에서 뉴스 불러오기")
  # 일주일치 자료 가져오기  
  query_str = 'select article_id, article_title, article_regtime from article where article_regtime >= date(now() - INTERVAL 6 DAY)'
  
  # DB로 저장
  db_connection = create_engine(db_connection_str)
  conn = db_connection.connect()
  
  result = pd.read_sql_query(text(query_str), conn)
  print("DB에서 일주일치 데이터 가져옴")
  
  week_df = spark.createDataFrame(result)
  print('스파크로 변환 완료 ')
  # print(type(week_df))
  # print(week_df.take(1))
  # print(week_df.printSchema())
  
  # 오늘 날짜 데이터 가져오기
  from pyspark.sql.functions import date_format
  # 임시로 20230328의 데이터를 오늘로 처리합니다.
  # today_df = week_df.filter(date_format('article_regtime', 'yyyyMMdd') == today).select('article_id', 'article_title', 'article_content')
  today_df = week_df.filter(date_format('article_regtime', 'yyyyMMdd') == '20230328').select('article_id', 'article_title')
  
  print("오늘 데이터 분리 완료")
  # print(today_df.take(1))
  
  return week_df, today_df

# 뉴스 전체 기사, 오늘 전체 기사 불러오기
def getTodayNews_hdfs(server, path, folder_path, sc, spark, db_connection_str):
  print("### 뉴스 기사 가져오기 ###")
  # 경로
  hdfs_path = server + path + folder_path # hdfs 폴더 저장 경로
  
  # hadoop 설정
  URI           = sc._gateway.jvm.java.net.URI
  Path          = sc._gateway.jvm.org.apache.hadoop.fs.Path
  FileSystem    = sc._gateway.jvm.org.apache.hadoop.fs.FileSystem
  Configuration = sc._gateway.jvm.org.apache.hadoop.conf.Configuration

  fs = FileSystem.get(URI(server), Configuration())
  
  # 빈 데이터 프레임
  from pyspark.sql.types import StructType, StructField, StringType
  mySchema = StructType([ StructField("article_id", StringType(), True)\
                        ,StructField("article_category", StringType(), True)\
                        ,StructField("article_regtime", StringType(), True)\
                        ,StructField("article_editor", StringType(), True)\
                        ,StructField("article_press", StringType(), True)\
                        ,StructField("article_title", StringType(), True)\
                        ,StructField("article_thumbnail", StringType(), True)\
                        ,StructField("article_content", StringType(), True)\
                        ,StructField("article_url", StringType(), True)\
                        ,StructField("article_hits", StringType(), True)])
  
  # 일주일치 뉴스
  df = spark.createDataFrame([], mySchema)
  # 오늘치 뉴스
  todaydf = None
  
  # 일주일치 경로가 있는지 확인
  date_obj = datetime.strptime(today, '%Y%m%d')
  date_list = [(date_obj - timedelta(days=x)).strftime('%Y%m%d') for x in range(6,-1,-1)]
  
  for days in date_list:
    print(days)
    if fs.exists(Path(hdfs_path + days + '.csv')):
      print("폴더가 존재합니다.")
      temp_df = spark.read.format('csv').option('header', True).option('multiLine', True).load(hdfs_path + days + '.csv')
      # daily_df = spark.read.option("multiLine",True).option("header", True).option("delimiter", ",").csv(hdfs_path + days + '.csv')
      # df = df.union(daily_df)
      # 오늘 날짜 뉴스 데이터
      # if days is date_list[-1]:
      # !!!!!!!!!!!! 임시로 20230328일자를 오늘 데이터로 처리합니다.
      if days == '20230328':
        print("20230328 일 입니다.")
        # todaydf = daily_df
    else:
      print("폴더가 존재하지 않습니다.")
      pass
  
  # 기사 아이디 순서로 정렬 후 파티션을 30개로 재조정
  # df = df.sort('article_id').repartition(30)
  # df = df.sort('article_id')
  
  print("### 일주일, 오늘 뉴스 기사 가져오기 완료 ###")
  
  # return df, todaydf
  return 0, 0

# 제거 함수 정의
def remove_unnecessary_words(text, stop_words):
  import re
  # <> [] 태그 내 단어, 따옴표, 한영제외한 기호 제거
  pattern = '<.+?>|\[.+?\]|[\'\"\‘\’\“\”,/·=]|[^a-zA-Z가-힣]'
  words = re.sub(pattern, ' ', text).split()
  
  filtered_words = [word for word in words if word.lower() not in stop_words]
  return ' '.join(filtered_words)

# 뉴스 기사에서 불용어 제거하기 (article_id, text)
def removeStopWords(df, path_str, type_str):
  print("### 불용어 제거 ###")
  # 파티션 수 재조정
  # df = df.repartition(3)
  
  # 제목과 내용 합쳐서 하나의 df로 만들기  
  from pyspark.sql.functions import concat_ws, col
  
  # 아이디, 텍스트 컬럼이 있는 spark df
  # 일주일 데이터는 기사 제목과 내용 합쳐서 text로, 오늘 데이터는 기사 제목만 선택해서 text로
  # type_str == 'week' -> article_title + article_content
  # type_str == 'today' -> article_title 
  # if type_str == 'week':
    # text_df = df.select('article_id', concat_ws(' ', 'article_title', 'article_content').alias('text'))
  # else:
    # text_df = df.select('article_id', col('article_title').alias('text'))
  
  # 타이틀만 선택. 타이틀을 'text'로 변환 
  text_df = df.select('article_id', col('article_title').alias('text'))
  
  # text 컬럼에서 정규표현식 사용
  from pyspark.sql.functions import udf
  from pyspark.sql.types import StringType
  
  print("불용어 설정")
  with open(path_str, 'r') as f:
    stop_words = [line.strip() for line in f.readlines()]
  print("불용어 설정 완료")
  
  # 태그와 불필요한 문자 제거, 불용어 제거
  print("불용어 제거 시작")
  start = time.time() # 시작 시간
  remove_words_udf = udf(lambda x: remove_unnecessary_words(x, stop_words), StringType())
  text_df = text_df.withColumn('text', remove_words_udf('text'))
  print(f">> 불용어 제거 소요 시간 : {time.time() - start:.5f} 초")
  
  return text_df

# 마지막 관련기사 아이디 불러오기
def readArticleId(path_str):
  print("### 마지막 관련기사 아이디 가져오기 ###")
  f = open(path_str, 'r')
  last_id = f.readlines()
  f.close()
  print("### 마지막 관련기사 아이디 가져오기 완료 ###")
  return last_id

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
  from pyspark.ml.feature import HashingTF, IDF, Tokenizer
  
  print("### TF-IDF 계산 ###")
  start = time.time() # 시작 시간
  
  tokenizer = Tokenizer(inputCol='text', outputCol='words')
  wordsData = tokenizer.transform(df)
  
  hashingTF = HashingTF(inputCol="words", outputCol="tf")
  tf = hashingTF.transform(wordsData)

  idf = IDF(inputCol="tf", outputCol="feature").fit(tf)
  tfidf = idf.transform(tf)
  
  # L2 norm
  from pyspark.ml.feature import Normalizer
  normalizer = Normalizer(inputCol="feature", outputCol="norm")
  data = normalizer.transform(tfidf)
  print(f">> TF-IDF 소요 시간 : {time.time() - start:.5f} 초")

  return data

# 유사도 검증
def getCosineSimilarity(nowData, totalData):
  import pyspark.sql.functions as psf
  from pyspark.sql.types import DoubleType
  from pyspark.sql.window import Window
  
  print("### 코사인 유사도 계산 ###")
  start = time.time() # 시작 시간

  dot_udf = psf.udf(lambda x,y: float(x.dot(y)), DoubleType())
  dot_df = nowData.alias("i").join(totalData.alias("j"), psf.col("i.article_id") != psf.col("j.article_id"))\
      .select(
          psf.col("i.article_id").alias("i"), 
          psf.col("j.article_id").alias("j"), 
          dot_udf("i.norm", "j.norm").alias("dot"))
  
  window = Window.partitionBy("i").orderBy(psf.desc("dot"))
  ranked_df = dot_df.select("*", psf.row_number().over(window).alias("rank")).filter("rank <= 6")
  
  # 가장 유사도가 높은 article_id 6개를 리스트로 반환 (article_id, sub_article_id[])
  result_df = ranked_df.groupBy("i").agg(psf.collect_list("j").alias("sub_article_id"))
  result_df = result_df.select(psf.col("i").alias("article_id"), psf.col("sub_article_id"))

  print(f">> 코사인 유사도 소요 시간 : {time.time() - start:.5f} 초")
  
  return result_df

def saveToDB(df, db_connection_str, spark):
  print("### 관련 기사 DB 저장 ###")
  # sub_article_id 리스트를 ','를 구분자로 하는 문자열로 변환
  print("아이디 리스트를 문자열로 변환")
  import pyspark.sql.functions as F
  df_str = df.withColumn("sub_article_id", F.concat_ws(",", "sub_article_id"))
  print("아이디 리스트를 문자열로 변환 완료")
  
  print("관련 기사 아이디들 판다스로 바꿀게요")
  pdDf = df_str.toPandas()
  print("관련 기사 데이터프레임 바꿨어요")
  # print(pdDf.take(1))
  
  # 결과 데이터프레임 csv에 저장해볼게요
  # print("관련기사 csv로 저장해볼게요")
  # df_str.coalesce(1).write.mode("append").option("header", True).csv("outrelated")
  
  print("관련 기사 DB 저장할게요")
  # DB로 저장
  db_connection = create_engine(db_connection_str)
  pdDf.to_sql(name='related_article', con=db_connection, if_exists='append',index=False)  
  
  print("### 관련 기사 DB 저장 완료 ###")
  
  # 마지막 인덱스 가져오기
  import pyspark.sql.functions as F

  last_id = None
  last_id = df_str.orderBy(F.col("article_id").desc()).select("article_id").first()[0]
  # print(last_id)
  
  return last_id

# 관련 기사 마지막 아이디 파일에 저장
def writeArticleId(path_str, id) :
  print("### 관련 기사 아이디 저장 ###")
  f = open(path_str, 'w')
  f.write(id)
  f.close()
  print("### 관련 기사 아이디 저장 완료 ###")
  return 0

def extract_nouns(sentence):
  # 공백 구분자
  nouns = sentence.split(" ")
  # 1글자 이상인 것만 반환  
  return [noun for noun in nouns if len(noun) > 1]

# 뉴스 파일 명사 추출
def getNounsByOneNews(data, sc, spark):
  print("### 오늘 뉴스 명사 추출 ###")
  
  one_rdd = data.select('text').rdd.flatMap(lambda x: x) # rdd로 만들기
  one_rdd = one_rdd.repartition(4)

  from pyspark.sql.types import StringType, ArrayType
  
  print("명사 추출하는 udf 실행")
  from pyspark.sql.functions import udf
  extract_nouns_udf = udf(extract_nouns, ArrayType(StringType()))

  start = time.time() # 시작 시간
  
  # 명사를 추출하고 한 행에 대해서 길이가 1 이상인 rdd만 반환
  nouns_rdd = one_rdd.map(lambda x: extract_nouns(x)).filter(lambda x: len(x) > 0)
  print(f">> 명사 추출 소요 시간 : {time.time() - start:.5f} 초")
  # print(nouns_rdd.take(1))
  
  start = time.time() # 시작 시간
  flat_rdd = nouns_rdd.flatMap(lambda x: x)
  print(f">> flat map  소요 시간 : {time.time() - start:.5f} 초")

  return flat_rdd

# word counting & word cloud
def setWordCloud(data, font_path, hdfs_path, spark):
  print("### Word Counting & Word Cloud 생성 ###")
  # wordcloud
  from wordcloud import WordCloud 
  
  start = time.time() # 시작 시간
  data2 = data.map(lambda x:(x,1)).groupByKey().mapValues(sum)
  print(f">> word counting 소요 시간 : {time.time() - start:.5f} 초")
  # print(data2.take(1))
  print("word cloud rdd >> dict 변환") 
  # rdd -> dict 설정
  data2_dict = data2.sortBy(lambda x: x[1], ascending=False).collectAsMap()
  
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
  cloud.to_file('../output/word_cloud.png')
  
  # hdfs에 날짜 폴더로 저장
  # save_df = spark.createDataFrame(data2)
  # save_df.repartition(1).write.mode("append").csv(hdfs_path+today)
  print("#### word cloud 저장 완료 ####")
  
  return 'success'
