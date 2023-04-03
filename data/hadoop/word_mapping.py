import datetime as dt
from pytz import timezone
import pandas as pd
from pandas import DataFrame, Series
import numpy as np
from konlpy.tag import Kkma

import time

import pymysql
import pandas as pd
from pandas import DataFrame, Series
from sqlalchemy import create_engine, text
pymysql.install_as_MySQLdb()
import MySQLdb

from pyspark.context import SparkContext
from pyspark.sql.session import SparkSession
import re
import os


def readLastArticleId():
    f = open("/home/ubuntu/data/crawling/last_article.txt", 'r')
    last_article = f.readlines()
    f.close()
    return last_article

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

# DB 연결
db_connection_str = 'mysql+pymysql://root:ssafy@j8a509.p.ssafy.io:3306/ssafy_cow_db'
db_connection = create_engine(db_connection_str)

# 데이터 조회 
# 경제 용어 조회
sql = "select * from economy_word order by word_id asc"
df = pd.DataFrame(db_connection.connect().execute(text(sql)))

# 시작 시간
starttime = time.time()
article_word_result = []
column_name = ["sub_word_id", "article_id"]
economy_words = df[['word','word_id']].values.tolist()
economy_word_map = dict(economy_words)
# print(economy_map)
# print(list(map((lambda x: x[0]), economy_words)))
# hdfs 뉴스 가져오기(마지막으로 처리한 다음 기사부터 워드 매핑 작업)
file_date = dt.datetime.today().astimezone(timezone('Asia/Seoul')).strftime("%Y%m%d") # 오늘 날짜 ex) 20230320

server = "hdfs://localhost:9000" # 서버
path = "/news/daily-news/" # 서버
hdfs_path = server + path # hdfs 폴더 저장 경로
today_path = hdfs_path+file_date+'.csv' # 오늘 날짜 csv 파일 경로

# 하둡 클러스터에서 실행
sc = SparkContext.getOrCreate()
spark = SparkSession(sc)

today_csv = spark.read.option("multiLine",True).option("header", True).option("sep", ",").csv(today_path)
news_df = today_csv.toPandas() # 오늘 csv
Kkma = Kkma()
# print(news_df)
last = int(readLastArticleId()[0])
print("마지막 기사아이디(여기서부터 작업시작): ",last)
start = news_df.index[(news_df['article_id'] == str(last+1))].tolist()[0]
# print("마지막 기사 아이디: ",last+1,"\n")
news_df = news_df[start:]
content_list  = news_df[['article_id','article_content']].values.tolist()
# content_list = [["<img src='https://imgnews.pstatic.net/image/020/2023/03/21/0003486509_001_20230321111601052.jpg?type=w647' /> ELS 효성첨단소재는 신규 대표이사로 조용수 부사장(63·사진)을 선임했다고 20일 밝혔다. 16일 정기 주주총회에서 사내이사로 선임된 조 부사장은 18일 이사회를 통해 대표이사 직에 올랐다. <br /><br /> 조 부사장은 연세대 행정학과를 졸업한 뒤 1987년 효성바스프로 입사해 영업, 마케팅, 기획, 전략 등을 두루 거쳤고 2008년 상무보로 승진해 타이어보강재 사업, 산업자재 부문 성장을 이끌었다. 2018년 6월부터 효성첨단소재 경영전략실장을 맡아 왔다.<br /><br /><div><strong>◇한국토지주택공사(LH)</strong></div> △부사장 박철흥 △상임이사 박동선 <br /><br />"]]
# 불용어 설정
stopwords = setStopWords('./stopword.txt')

for idx, article in enumerate(content_list):
    detail = {}
    content = str(article[1])
#     print("original:\n", content)
    # 이미지 태그 등 부가 태그들에 대한 텍스트는 제거
    filtered_content = re.sub('<div.+?/div>', '', content)
    filtered_content = re.sub('<img.+?/>', '', filtered_content)
    filtered_content = re.sub('<em.+?/em>', '', filtered_content)
    filtered_content = re.sub('<br />', '', filtered_content)
    
    clean_words = [] 
    news_word_list = list()

    # 품사 및 명사 분석
    words_tags = Kkma.pos(filtered_content)
    article_words = Kkma.nouns(filtered_content)

    for word in words_tags:
        if word[1] == "OL":
            article_words.append(word[0])

    # 불용어 제거 기사 단어
    for word in article_words:
        if word not in stopwords:
            clean_words.append(word)

    word_list = list(set(list(map((lambda x: x[0]), economy_words))).intersection(clean_words))
    word_list.sort(key=lambda x: len(x),reverse=True)
    article_word = ""; # 기사 출현 경제 용어 리스트
    
    for index, word in enumerate(word_list):
        content = content.replace(word, f"@@{index}@@")

    # 다시 정렬된 list를 돌면서 나머지는 {단어}로 돌려둔다.
    for index, word in enumerate(word_list):
        #  @@{list에서 단어 index}@@의 첫 번째는 <span>{단어}</span>로 바꾸고
        content = content.replace(f"@@{index}@@", f"<span>{word}</span>", 1)
        if (f"<span>{word}</span>") in content:
            article_word += (str(economy_word_map.get(word)) + ", ")
        # 나머지는 {단어}로 돌려둔다.
        content = content.replace(f"@@{index}@@", word)
    
    if len(article_word) > 0:
        detail["article_id"] = article[0]
        detail["sub_word_id"] = article_word[:-2]
        article_word_result.append(detail)
#     print("result:\n", content)
    news_df.loc[start+idx, 'article_content'] = content

# print(news_df)
# print(article_word_result)
word_df = pd.DataFrame(article_word_result, columns=column_name)
# DB로 기사 저장
news_df.to_sql(name='article', con=db_connection, if_exists='append',index=False)  
# 기사 출현 경제 용어 저장
word_df.to_sql(name='article_word', con=db_connection, if_exists='append',index=False)

# 종료 시간
endtime = time.time()

print(f'소요 시간 {endtime - starttime}초')