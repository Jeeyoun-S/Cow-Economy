from urllib.request import urlopen
from bs4 import BeautifulSoup, NavigableString, Tag
import datetime as dt
from pytz import timezone

import pymysql
import pandas as pd
from pandas import DataFrame, Series
from sqlalchemy import create_engine, text
pymysql.install_as_MySQLdb()
import MySQLdb
from tqdm import tqdm
import pickle
import time

from pyspark.context import SparkContext
from pyspark.sql.session import SparkSession

import os

def writeLastArticleId(article) :
    f = open("/home/ubuntu/data/crawling/test.txt", 'w')
    print(article)
    f.write(article)
    f.close()
    
sc = SparkContext.getOrCreate()
spark = SparkSession(sc)
# hdfs 뉴스 가져오기(마지막으로 처리한 다음 기사부터 워드 매핑 작업)
file_date = dt.datetime.today().astimezone(timezone('Asia/Seoul')).strftime("%Y%m%d") # 오늘 날짜 ex) 20230320

server = "hdfs://localhost:9000" # 서버
path = "/news/daily-news/" # 서버
hdfs_path = server + path # hdfs 폴더 저장 경로
today_path = hdfs_path+file_date+'.csv' # 오늘 날짜 csv 파일 경로
toady_csv = spark.read.option("multiLine",True).option("header", True).option("sep", ",").csv(today_path)
# 판다스데이터프레임 = 오늘 날짜 csv + 지금 크롤링한 뉴스
today_pd = toady_csv.toPandas() # 오늘 csv
print(today_pd.tail(2))

writeLastArticleId("테스트")