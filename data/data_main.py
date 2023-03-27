import data_sub

from pyspark.context import SparkContext
from pyspark.sql.session import SparkSession

sc = SparkContext.getOrCreate()
spark = SparkSession(sc)

# ----------- 경로 설정 ----------
server = "hdfs://localhost:9000" # 로컬
# server = "hdfs://cluster.p.ssafy.io:9000" # 서버
path = "/user/hadoop/news/" # 로컬
# path = "/user/j8a509/news/" # 서버
hdfs_path = server + path # hdfs 폴더 저장 경로

daily_news = 'daily_news/'
word_cloud = 'word-cloud/'

stop_word_path = './stopword.txt'
related_id_path = './related_last.txt'
font_path = './assets/NotoSansKR-Black.otf'

# 로컬
db_connection_str = 'mysql+pymysql://root:root@localhost:3306/ssafy_cow_db'
# 서버
# db_connection_str = 'mysql+pymysql://root:ssafy@j8a509.p.ssafy.io:3306/ssafy_cow_db'
# ----------- 경로 설정 끝 ----------

# 뉴스 전체 기사, 오늘 전체 기사 불러오기 -> spark df
week_total, today_total = data_sub.getToadyNews(server, path, daily_news, sc, spark)

# 뉴스 기사에서 불용어 제거하기
week_total = data_sub.removeStopWords(week_total, stop_word_path)

# 마지막 관련기사 아이디 불러오기
last_idx = data_sub.readArticleId(related_id_path)[0]

# 방금 크롤링한 자료, 일주일치 전체뉴스로 분리
nowDf, week_total = data_sub.getNowCrawlNews(week_total, last_idx)

# ---------- 관련 기사 ----------
# 방금 크롤링, 전체 뉴스 TF-IDF
nowTfidf = data_sub.getTFIDF(nowDf)
totalTfidf = data_sub.getTFIDF(week_total)

# 방금 크롤링에 대해서 전체 유사도 검사
related_df = data_sub.getCosineSimilarity(nowTfidf, totalTfidf)

# DB에 저장
last_idx = data_sub.saveToDB(related_df, db_connection_str)

# 관련 기사 마지막 아이디 파일에 저장
data_sub.writeArticleId(related_id_path, last_idx)

# ---------- word cloud ----------
# 오늘 뉴스 불용어 제거
today_total = data_sub.removeStopWords(today_total, stop_word_path)
# 오늘 뉴스 명사 추출
data_nouns = data_sub.getNounsByOneNews(today_total, sc, spark)

# word cloud 생성, hdfs에 word cloud dict 적재
str = data_sub.setWordCloud(data_nouns, font_path, hdfs_path + word_cloud, spark)
