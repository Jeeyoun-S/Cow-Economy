import data_sub

hdfs_path = 'hdfs://localhost:9000/user/hadoop/news/'
# hdfs_path = 'hdfs://cluster.p.ssafy.io:9000/user/j8a509/news/daily-news/'

# 뉴스 기사 가져오기
data, nowData = data_sub.getNewsFromHdfs(hdfs_path + 'daily-news/')
# data.show()
# print(data.take(1))

# 불용어 설정
stopword = data_sub.setStopWords('./stopword.txt')
# print(stopword)
# print(len(stopword))

# 명사 추출
data_nouns = data_sub.getNounsByOneNews(data)
# print(data_nouns.collect())

# 불용어 제거
data_nouns = data_sub.removeStopWordFromNews(data_nouns, stopword)

# word cloud 생성, hdfs에 word cloud dict 적재
font_path = './assets/NotoSansKR-Black.otf'
hdfs_path = hdfs_path + 'word-cloud/'
str = data_sub.setWordCloud(data_nouns, font_path, hdfs_path)

# TF-IDF
feature_vect, original = data_sub.getTFIDF(data)
# feature_vect : 모든 뉴스 데이터의 단어 백터
# original : data의 아이디, 제목, 내용만 있는 데이터

# 코사인 유사도 -> 모든 뉴스 기사에 대해서
data_sub.getCosineSimilarity(feature_vect, original)