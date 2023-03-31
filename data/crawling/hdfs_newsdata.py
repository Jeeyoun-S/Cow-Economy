import datetime as dt
from pytz import timezone
import pandas as pd
from pandas import DataFrame, Series
import time

from pyspark.context import SparkContext
from pyspark.sql.session import SparkSession

import os

def put_data(news_df) : 
    sc = SparkContext.getOrCreate()
    spark = SparkSession(sc)

    # 경로
#     server = "hdfs://localhost:9000" # 로컬
    server = "hdfs://cluster.p.ssafy.io:9000" # 서버
#     path = "/user/hadoop/test" # 로컬
    path = "/user/j8a509/news/daily-news/" # 서버
    hdfs_path = server + path # hdfs 폴더 저장 경로

    file_date = dt.datetime.today().astimezone(timezone('Asia/Seoul')).strftime("%Y%m%d") # 오늘 날짜 ex) 20230320

    # hadoop 설정
    URI           = sc._gateway.jvm.java.net.URI
    Path          = sc._gateway.jvm.org.apache.hadoop.fs.Path
    FileSystem    = sc._gateway.jvm.org.apache.hadoop.fs.FileSystem
    Configuration = sc._gateway.jvm.org.apache.hadoop.conf.Configuration

    fs = FileSystem.get(URI(server), Configuration())
    today_path = hdfs_path+file_date+'.csv' # 오늘 날짜 csv 파일 경로

    # HDFS에 오늘 날짜 csv 있는지 확인
    flag = fs.exists(Path(today_path))
    today_pd=news_df
    # 파일 존재 확인
    if flag:
      # 오늘날짜 파일 불러오기
      toady_csv = spark.read.option("multiLine",True).option("header", True).option("sep", ",").csv(today_path)
      # 판다스데이터프레임 = 오늘 날짜 csv + 지금 크롤링한 뉴스
      today_pd = toady_csv.toPandas() # 오늘 csv
      print(today_pd+"\n")
      today_pd = today_pd.append(news_df, ignore_index=True) # 오늘 날짜 csv + 지금 크롤링한 뉴스
      print(today_pd)
      # 오늘 날짜 파일 삭제
      fs.delete(Path(today_path), True)
      print("파일있음. 원본 파일 삭제함")
    else:
      # 오늘 날짜만 설정
      today_pd = news_df
      print("파일없음")

    # hdfs로 저장
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

    save_df = spark.createDataFrame(today_pd, schema=mySchema)
    save_df.repartition(1).write.mode("overwrite").options(header='True').csv(today_path)
