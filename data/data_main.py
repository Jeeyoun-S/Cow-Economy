import data_sub
import related_news

from pyspark.context import SparkContext
from pyspark.sql.session import SparkSession

sc = SparkContext.getOrCreate()
spark = SparkSession(sc)

hdfs_path = 'hdfs://localhost:9000/user/hadoop/news/'

# 뉴스 전체 기사 불러오기 -> spark df
todayTotal = related_news.getToadyNews(hdfs_path+'/daily_news/', sc, spark)
# todayTotal.show()
# print(todayTotal.count())
todayTotal = todayTotal.repartition(50)

# 뉴스 기사에서 불용어 제거하기
todayTotal = related_news.removeStopWords(todayTotal, './stopword.txt')

# 마지막 관련기사 아이디 불러오기
path_str = './related_last.txt'
last_idx = related_news.readArticleId(path_str)[0]

# 오늘자료, 전체뉴스로 분리
nowDf, todayTotal = related_news.getNowCrawlNews(todayTotal, last_idx)
print('파티션수 재조정')
todayTotal = todayTotal.repartition(50)
nowDf = nowDf.repartition(50)
# nowDf.show()
# todayTotal.show()

# 전체 뉴스 TF-IDF
nowTfidf = related_news.getTFIDF(nowDf)
totalTfidf = related_news.getTFIDF(todayTotal)

# 방금 크롤링에 대해서 전체 유사도 검사
print('유사도 검사중')
related_df = related_news.getCosineSimilarity(nowTfidf, totalTfidf)
# related_df.show()

# 테스트 출력
# joined_df = nowDf.join(related_df, on=['article_id'], how='inner')
# joined_df.select('article_id', 'text').show()

# DB에 저장
last_idx = related_news.saveToDB(related_df)
path_str = './related_last.txt'
related_news.writeArticleId(path_str, last_idx)

# 불용어 제거한 todayTotal를 가지고 Mecab 돌려서 워드클라우드 저장

# 명사 추출
data_nouns = data_sub.getNounsByOneNews(todayTotal)
# print(data_nouns.collect())

# word cloud 생성, hdfs에 word cloud dict 적재
font_path = './assets/NotoSansKR-Black.otf'
hdfs_path = hdfs_path + 'word-cloud/'
str = data_sub.setWordCloud(data_nouns, font_path, hdfs_path)
