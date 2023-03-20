import before_TFIDF

hdfs_path = 'hdfs://localhost:9000/user/hadoop/news/'

# 뉴스 기사 가져오기
data = before_TFIDF.getNewsFromHdfs(hdfs_path)
# data.show()

# 불용어 설정
stopword = before_TFIDF.setStopWords('./stopword.txt')
# print(stopword)
# print(len(stopword))

# 명사 추출
data = before_TFIDF.getNounsByOneNews(data)
# print(news_word_data.collect())

# 불용어 제거
data = before_TFIDF.removeStopWordFromNews(data, stopword)
# print(data.collect())

# 하나의 뉴스 단어로 만들기
total_news_word = before_TFIDF.setOneList(data)
# print(total_news_word)

# word cloud 생성
font_path = './assets/NotoSansKR-Black.otf'
str = before_TFIDF.setWordCloud(total_news_word, font_path)

# TF-IDF
# tfidf, word_tfidf = before_TFIDF.setTFIDF(data)
# print(tf_idf.collect())
# print(word_tfidf.collect())