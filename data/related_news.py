# 테스트용
from datetime import datetime
from pytz import timezone

import pandas as pd
from tqdm import tqdm

import re

today = datetime.today().astimezone(timezone('Asia/Seoul')).strftime("%Y%m%d") # 오늘 날짜 ex) 20230320

def readArticleId(path_str):
    f = open(path_str, 'r')
    last_id = f.readlines()
    f.close()
    return last_id

def writeArticleId(path_str, id) :
    f = open(path_str, 'w')
    f.write(id)
    f.close()

# 오늘 날짜 기사 불러오기
def getToadyNews(path_str, sc, spark):
  df = spark.read.option("multiLine",True).option("header", True).option("sep", ",").csv(path_str+"20230323.csv")
  
  # 기사 아이디 순서로 정렬해서 보냄
  df = df.sort('article_id')
  return df

# 제거 함수 정의
def remove_unnecessary_words(text, stop_words):
  pattern = '<.+?>|[\'\"\‘\’\“\”,/·=]'
  words = re.sub(pattern, ' ', text).split()
  
  filtered_words = [word for word in words if word.lower() not in stop_words]
  return ' '.join(filtered_words)

# 뉴스 제목과 내용을 합쳐 불용어 제거 후 아이디와 text로 반환
def removeStopWords(df, path_str):
  # 제목과 내용 합쳐서 하나의 df로 만들기  
  from pyspark.sql.functions import concat_ws
  
  # 아이디, 텍스트 컬럼이 있는 spark df
  text_df = df.select('article_id', concat_ws(' ', 'article_title', 'article_content').alias('text'))
  # text_df.show()
  # print(text_df.take(1))
  
  # text 컬럼에서 정규표현식 사용. html 태그와 따옴표 , / 제거
  from pyspark.sql.functions import udf
  from pyspark.sql.types import StringType
  
  with open(path_str, 'r') as f:
    stop_words = [line.strip() for line in f.readlines()]
  
  # 태그와 불필요한 문자 제거, 불용어 제거
  remove_words_udf = udf(lambda x: remove_unnecessary_words(x, stop_words), StringType())
  text_df = text_df.withColumn('text', remove_words_udf('text'))
  # text_df.show()
  # print(text_df.take(1))
  
  return text_df

# 오늘의 전체 뉴스 가지고 방금 크롤링한 뉴스 반환
def getNowCrawlNews(df, idx):
  # 아이디 기준 정렬
  df = df.sort('article_id')

  nowDay = df.filter(df.article_id > idx)
  # nowDay.show()

  return nowDay, df

# 각 TF-IDF를 구하는 함수. 데이터를 반환
def getTFIDF(df):
  # TF-IDF
  from pyspark.ml.feature import HashingTF, IDF, Tokenizer
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

  return data

# 유사도 검증
def getCosineSimilarity(nowData, totalData):
  # matrix product
  import pyspark.sql.functions as psf
  from pyspark.sql.types import DoubleType
  from pyspark.sql.window import Window

  dot_udf = psf.udf(lambda x,y: float(x.dot(y)), DoubleType())
  dot_df = nowData.alias("i").join(totalData.alias("j"), psf.col("i.article_id") != psf.col("j.article_id"))\
      .select(
          psf.col("i.article_id").alias("i"), 
          psf.col("j.article_id").alias("j"), 
          dot_udf("i.norm", "j.norm").alias("dot"))
  
  # rank j values within each i group based on dot value
  window = Window.partitionBy("i").orderBy(psf.desc("dot"))
  ranked_df = dot_df.select("*", psf.row_number().over(window).alias("rank")).filter("rank <= 6")
  
  result_df = ranked_df.groupBy("i").agg(psf.collect_list("j").alias("sub_article_id"))
  result_df = result_df.select(psf.col("i").alias("article_id"), psf.col("sub_article_id"))

  return result_df

def saveToDB(df):
  # df.show()
  # 문자열로 변환
  import pyspark.sql.functions as F
  df_str = df.withColumn("sub_article_id", F.concat_ws(",", "sub_article_id"))
  # df_str.show()
  
  import pymysql
  import pandas as pd
  from sqlalchemy import create_engine
  pymysql.install_as_MySQLdb()
  import MySQLdb
  
  pdDf = df_str.toPandas()
  
  # DB로 저장
  db_connection_str = 'mysql+pymysql://root:root@localhost:3306/ssafy_cow_db'
  db_connection = create_engine(db_connection_str)
  # pdDf.to_sql(name='related_article', con=db_connection, if_exists='append',index=False)  
  
  # 마지막 인덱스 가져오기
  import pyspark.sql.functions as F

  last_id = df_str.orderBy(F.col("article_id").desc()).select("article_id").first()[0]
  print(last_id)
  
  return last_id