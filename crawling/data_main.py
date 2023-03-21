import data_sub

hdfs_path = 'hdfs://localhost:9000/user/hadoop/news/' # 로컬
# hdfs_path = 'hdfs://cluster.p.ssafy.io:9000/user/j8a509/news/daily-news/' # 서버

# 뉴스 기사 가져오기 (전체뉴스, 방금 크롤링한 뉴스)
data, nowData = data_sub.getNewsFromHdfs(hdfs_path)
# data.show()

# 불용어 설정
stopword = data_sub.setStopWords('./stopword.txt')
# print(stopword)
# print(len(stopword))

# 명사 추출
data_nouns = data_sub.getNounsByOneNews(nowData)
# print(news_word_data.collect())

# 불용어 제거
data_nouns = data_sub.removeStopWordFromNews(data_nouns, stopword)
# print(data.collect())

# 하나의 뉴스 단어로 만들기
total_news_word = data_sub.setOneList(data_nouns)
# print(total_news_word)

# word cloud 생성
font_path = './assets/NotoSansKR-Black.otf'
hdfs_path = hdfs_path + 'word-cloud/'
str = data_sub.setWordCloud(total_news_word, font_path, hdfs_path)