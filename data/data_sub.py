# ----------- 설정 -----------
# spark
from pyspark.context import SparkContext
from pyspark.sql.session import SparkSession

sc = SparkContext.getOrCreate()
spark = SparkSession(sc)

# Mecab
from konlpy.tag import Mecab
mecab = Mecab()

# wordcloud
from wordcloud import WordCloud

# TF-IDF
from pyspark.mllib.feature import HashingTF, IDF
from math import log

# Pandas
import pandas as pd

from tqdm import tqdm

from datetime import datetime
from pytz import timezone

today = datetime.today().astimezone(timezone('Asia/Seoul')).strftime("%Y%m%d") # 오늘 날짜 ex) 20230320

# DB
import pymysql
from sqlalchemy import create_engine, text
pymysql.install_as_MySQLdb()
import MySQLdb

# ----------- 설정 끝 -----------

# 뉴스 파일 명사 추출
def getNounsByOneNews(data):
  # 뉴스 데이터에서 타이틀과 내용만 가져와서 하나의 데이터 프레임으로 변환
  data = data.select('text').toPandas()
  data = pd.DataFrame(data)
  df = spark.createDataFrame(data)
  # df.show()
  
  # 하나의 리스트로 만들기
  news_list = df.rdd.flatMap(lambda x: x).collect()
  
  news_word_list = list()
  for val in news_list:
    news_word_list.append(mecab.nouns(val))
  
  # 1글자 제거 후 하나의 리스트
  news_word_list_except_one = [word for word_list in news_word_list for word in word_list if len(word) > 1]
  print("#### 명사 추출 완료 ####")
  
  # RDD 변환
  news_word_list_rdd = sc.parallelize(news_word_list_except_one)
  return news_word_list_rdd


# word counting & word cloud
def setWordCloud(data, font_path, hdfs_path):
  data2 = data.map(lambda x:(x,1)).groupByKey().mapValues(sum).sortByKey(True)
  print("#### word count 완료 ####")
  
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
  # save_df.repartition(1).write.mode("append").csv(hdfs_path+today)
  print("#### word cloud 완료 ####")
  
  return 'success'
