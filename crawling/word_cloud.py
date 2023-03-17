from pyspark.context import SparkContext
from pyspark.sql.session import SparkSession

sc = SparkContext.getOrCreate()
spark = SparkSession(sc)

path_str = 'hdfs://localhost:9000/user/hadoop/news/'
df = spark.read.option("multiLine",True).option("header", True).option("sep", ",").csv(path_str+"*.csv")
print("#### 데이터 가져오기 완료 ####")
print(f"{df.count()}개")

# word cloud
# Mecab
from konlpy.tag import Mecab
mecab = Mecab()

# wordcloud
from wordcloud import WordCloud

# 파일 열기 설정
def openfile(path):
    res = list()

    f = open(path, 'r', encoding='UTF-8')
    lines = f.readlines()
    for line in lines:
        res.append(line.replace('\n', ''))
    return res

# 불용어 설정
stop_words = openfile('./stopword.txt')
print("#### 불용어 설정 완료 ####")

# 뉴스 내용 리스트 설정
my_news = df.select('article_content').rdd.flatMap(lambda x: x).collect()
print("#### 뉴스 설정 완료 ####")
# print(my_news)

# 시간 측정
import time
start = time.time() # 시작 시간

# 뉴스 파일 명사 추출
nouns_list = list()
for news in my_news:
    nouns_list += mecab.nouns(news)
print("#### 명사 추출 완료 ####")
# print(nouns_list)
print(f"명사 추출 소요 시간 : {time.time() - start:.5f} 초")

# RDD로 변환
myRdd = sc.parallelize(nouns_list)
print("#### RDD 변환 완료 ####")
# print(myRdd.collect())

# 불용어 제거
myRdd_stop1 = myRdd.filter(lambda x:x.lower() not in stop_words)
print("#### 불용어 제거 완료 ####")
# print(myRdd_stop1.collect())

start = time.time()

# Word count
myRdd_stop2 = myRdd_stop1.map(lambda x:(x,1)).groupByKey().mapValues(sum).sortByKey(True)
print("#### word count 완료 ####")
# print(myRdd_stop2.collect())
print(f"word count 소요 시간 : {time.time() - start:.5f} 초")

# wordcloud
wc = WordCloud(font_path="./NotoSansKR-Black.otf",
               background_color='white',
               height=600,
               width=1000,
               max_words=400,
               max_font_size=100,
               colormap='Set3_r',
            #    mask=mask
               )

# rdd -> dict 설정
myRdd_stop2_dict = myRdd_stop2.collectAsMap()

cloud = wc.generate_from_frequencies(myRdd_stop2_dict)

# 이미지 파일 저장
cloud.to_file('word_cloud.png')
