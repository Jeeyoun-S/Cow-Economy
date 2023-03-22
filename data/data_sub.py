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

# TF-IDF, cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

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
  stopword.update(['<', '/>', 'br', 'img', 'src', '<em>'])
  
  f = open(path, 'r', encoding='UTF-8')
  lines = f.readlines()
  
  for line in lines:
    stopword.add(line.replace('\n', ''))
  print("#### 불용어 설정 완료 ####")
  return stopword

# 뉴스 파일 명사 추출
def getNounsByOneNews(data):
  # 뉴스 데이터에서 타이틀과 내용만 가져와서 하나의 데이터 프레임으로 변환
  data = data.select('article_title', 'article_content').toPandas()
  data = data['article_title'] + " " + data['article_content']
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

# 불용어 제거
def removeStopWordFromNews(data, stop_word):
  news_list = data.filter(lambda x: x.lower() not in stop_word)
  print("#### 불용어 제거 완료 ####")
  return news_list

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
  save_df.repartition(1).write.mode("append").csv(hdfs_path+today)
  print("#### word cloud 완료 ####")
  
  return 'success'

# TF-IDF & 유사도
def getTFIDF(data):
  # Spark Dataframe -> Pandas Dataframe (article_id, article_title article_content)
  original = data.select('article_id', 'article_title', 'article_content')
  
  # pandas로 변환
  original = original.toPandas()
  # title, content 합쳐서 임시 리스트에 담기
  df_text_list = original['article_title'] + " " + original['article_content']
  
  # TF-IDF 사용해서 등장 빈도 체크 후 저장
  tfidf_vect = TfidfVectorizer()
  feature_vect = tfidf_vect.fit_transform(df_text_list)

  return feature_vect, original

def getCosineSimilarity(feature_vect, pddf):
  # db 연결 설정
  # DB 연결
  db_connection_str = 'mysql+pymysql://root:root@localhost:3306/ssafy_cow_db'
  db_connection = create_engine(db_connection_str)
  
  # 테스트용 첫번째 기사와 모두 비교
  # 모든 뉴스에 대해서 모든 기사 비교하기
  related_list = list()
  for feat in tqdm(feature_vect):
    # 코사인 유사도
    similarity_simple_pair = cosine_similarity(feat, feature_vect)
    result_list = similarity_simple_pair.tolist()[0]
    
    # 유사도 결과 컬럼 추가
    pddf['similarity_result'] = result_list
    print(pddf)
    # 11개를 가져오고 가장 상위는 자기 기사 아이디로, 하위 10개는 관련기사로 처리
    related_data = pddf.sort_values(by='similarity_result', ascending=False).head(7)
    # 기사 id
    article_id = related_data.iloc[0]['article_id']
    # 관련 기사 id
    sub_article_id = related_data.iloc[1:7]['article_id'].to_string(header=False, index=False).replace('\n', ',')
    related_list.append([article_id, sub_article_id])

  # DB 저장
  column_name = ['article_id', 'sub_article_id']
  related_df = pd.DataFrame(related_list, columns=column_name)
  related_df.to_sql(name='related_article', con=db_connection, if_exists='append',index=False)  

  return 'success'