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

# 시간 설정
from datetime import datetime
from pytz import timezone

today = datetime.today().astimezone(timezone('Asia/Seoul')).strftime("%Y%m%d") # 오늘 날짜 ex) 20230320

# ----------- 설정 끝 -----------

# HDFS에서 기사 가져오기
def getNewsFromHdfs(path_str):
   # 모든 뉴스 데이터
  df = spark.read.option("multiLine",True).option("header", True).option("sep", ",").csv(path_str+"*.csv")
  # 오늘 크롤링한 데이터
  nowDay = spark.read.option("multiLine",True).option("header", True).option("sep", ",").csv(path_str+today+".csv")
  
  print("#### 데이터 가져오기 완료 ####")
  print(f"전체 데이터 {df.count()}개")
  print(f"방금 크롤링한 데이터 {nowDay.count()}개")
  return df, nowDay

# 불용어 설정
def setStopWords(path):
  stopword = set()
  
  # 구분자 설정
  sep_div = '@@div'
  sep_img = '@@divimg'
  sep_desc = '@@divimgdesc'
  
  stopword.add(sep_div)
  stopword.add(sep_img)
  stopword.add(sep_desc)
  
  f = open(path, 'r', encoding='UTF-8')
  lines = f.readlines()
  
  for line in lines:
    stopword.add(line.replace('\n', ''))
  print("#### 불용어 설정 완료 ####")
  return stopword

# 뉴스 파일 명사 추출
def getNounsByOneNews(data):
  news_data = data.select('article_content').rdd.flatMap(lambda x: x).collect()
  
  news_word_list = list()
  for val in news_data:
    news_word_list.append(mecab.nouns(val))
  print("#### 명사 추출 완료 ####")
  
  # RDD 변환
  news_word_list_rdd = sc.parallelize(news_word_list)
  return news_word_list_rdd

# 불용어 제거
def removeStopWordFromNews(data, stop_word):
  news_list = data.map(lambda x: [w for w in x if w.lower() not in stop_word])
  print("#### 불용어 제거 완료 ####")
  return news_list

# 뉴스 전체 단어 리스트 만들기
def setOneList(data):
  total_data = data.flatMap(lambda x: x)
  print("#### 뉴스 단어 합치기 완료 ####")
  return total_data

# word counting & word cloud
def setWordCloud(data, font_path, hdfs_path):
  data2 = data.map(lambda x:(x,1)).groupByKey().mapValues(sum).sortByKey(True)
  print("#### word count 완료 ####")
  
  # rdd -> dict 설정
  data2_dict = data2.collectAsMap()
  
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
  
  return 'success'
