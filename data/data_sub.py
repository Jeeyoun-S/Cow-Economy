from tqdm import tqdm
import pandas as pd

from datetime import datetime, timedelta
from pytz import timezone

# 시간 측정
import time

# 오늘 날짜 ex) 20230320
today = datetime.today().astimezone(timezone('Asia/Seoul')).strftime("%Y%m%d")

# 뉴스 전체 기사, 오늘 전체 기사 불러오기
def getToadyNews(server, path, folder_path, sc, spark):
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
  from pyspark.sql.types import StructType,StructField, StringType
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
  
  for days in tqdm(date_list):
    if fs.exists(Path(hdfs_path + days + '.csv')):
      daily_df = spark.read.option("multiLine",True).option("header", True).option("sep", ",").csv(hdfs_path + days + '.csv')
      df = df.union(daily_df)
      # 오늘 날짜 뉴스 데이터
      if days is date_list[-1]:
        todaydf = daily_df
    else:
      pass
  
  # 기사 아이디 순서로 정렬 후 파티션을 30개로 재조정
  df = df.sort('article_id').repartition(30)
  
  print("### 일주일, 오늘 뉴스 기사 가져오기 완료 ###")
  
  # 임시로 오늘 뉴스 더미데이터
  todaydf = df
  
  return df, todaydf

# 제거 함수 정의
def remove_unnecessary_words(text, stop_words):
  import re
  # <> 태그 내 단어, 따옴표 등 기호 제거
  pattern = '<.+?>|[\'\"\‘\’\“\”,/·=]'
  words = re.sub(pattern, ' ', text).split()
  
  filtered_words = [word for word in words if word.lower() not in stop_words]
  return ' '.join(filtered_words)

# 뉴스 기사에서 불용어 제거하기 (article_id, text)
def removeStopWords(df, path_str):
  print("### 불용어 제거 ###")
  # 제목과 내용 합쳐서 하나의 df로 만들기  
  from pyspark.sql.functions import concat_ws
  
  # 아이디, 텍스트 컬럼이 있는 spark df
  text_df = df.select('article_id', concat_ws(' ', 'article_title', 'article_content').alias('text'))
  
  # text 컬럼에서 정규표현식 사용. html 태그와 따옴표 , / 제거
  from pyspark.sql.functions import udf
  from pyspark.sql.types import StringType
  
  with open(path_str, 'r') as f:
    stop_words = [line.strip() for line in f.readlines()]
  
  # 태그와 불필요한 문자 제거, 불용어 제거
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
  df = df.sort('article_id').repartition(30)
  
  nowDay = df.filter(df.article_id > idx).repartition(30)

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

def saveToDB(df, db_connection_str):
  print("### 관련 기사 DB 저장 ###")
  # sub_article_id 리스트를 ','를 구분자로 하는 문자열로 변환
  import pyspark.sql.functions as F
  df_str = df.withColumn("sub_article_id", F.concat_ws(",", "sub_article_id"))
  
  import pymysql
  from sqlalchemy import create_engine
  pymysql.install_as_MySQLdb()
  import MySQLdb
  
  pdDf = df_str.toPandas()
  
  # DB로 저장
  db_connection = create_engine(db_connection_str)
  pdDf.to_sql(name='related_article', con=db_connection, if_exists='append',index=False)  
  
  print("### 관련 기사 DB 저장 완료 ###")
  
  # 마지막 인덱스 가져오기
  import pyspark.sql.functions as F

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

# 뉴스 파일 명사 추출
def getNounsByOneNews(data, sc, spark):
  print("### 오늘 뉴스 명사 추출 ###")
  data = data.select('text').toPandas()
  data = pd.DataFrame(data)
  df = spark.createDataFrame(data)
  
  # Mecab
  from konlpy.tag import Mecab
  mecab = Mecab()
  
  # 하나의 리스트로 만들기
  news_list = df.rdd.flatMap(lambda x: x).collect()
  
  start = time.time() # 시작 시간
  
  news_word_list = list()
  for val in news_list:
    news_word_list.append(mecab.nouns(val))
  
  # 1글자 제거 후 하나의 리스트
  news_word_list_except_one = [word for word_list in news_word_list for word in word_list if len(word) > 1]
  print(f">> 명사 추출 소요 시간 : {time.time() - start:.5f} 초")
  
  # RDD 변환
  news_word_list_rdd = sc.parallelize(news_word_list_except_one)
  return news_word_list_rdd

# word counting & word cloud
def setWordCloud(data, font_path, hdfs_path, spark):
  print("### Word Cloud 생성 ###")
  # wordcloud
  from wordcloud import WordCloud 
  
  start = time.time() # 시작 시간
  data2 = data.map(lambda x:(x,1)).groupByKey().mapValues(sum).sortByKey(True)
  print(f">> word counting 소요 시간 : {time.time() - start:.5f} 초")
  
  # rdd -> dict 설정
  data2_dict = data2.sortBy(lambda x: x[1], ascending=False).collectAsMap()
  
  # word cloud 설정
  wc = WordCloud(font_path=font_path,
                 background_color='white',
                 height=600,
                 width=1000,
                 max_words=400,
                 max_font_size=100,
                 colormap='Set3_r')
  cloud = wc.generate_from_frequencies(data2_dict)
  
  # 파일 저장
  cloud.to_file('word_cloud.png')
  
  # hdfs에 날짜 폴더로 저장
  save_df = spark.createDataFrame(data2)
  save_df.repartition(1).write.mode("append").csv(hdfs_path+today)
  print("#### word cloud 저장 완료 ####")
  
  return 'success'