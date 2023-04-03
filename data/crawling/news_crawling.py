from urllib.request import urlopen
from urllib.error import URLError, HTTPError
from bs4 import BeautifulSoup, NavigableString, Tag
import datetime as dt
from pytz import timezone

import pymysql
import pandas as pd
from pandas import DataFrame, Series
from sqlalchemy import create_engine, text
pymysql.install_as_MySQLdb()
import MySQLdb
import pickle
import time

from pyspark.context import SparkContext
from pyspark.sql.session import SparkSession

import os

import hdfs_newsdata as newsdata

def readArticleTime():
    f = open("/home/ubuntu/data/crawling/article_time.txt", 'r')
    last_article_time = f.readlines()
    f.close()
    return last_article_time

def writeArticleTime(dates) :
    f = open("/home/ubuntu/data/crawling/article_time.txt", 'w')
    print(dates)
    f.write(dates)
    f.close()

def writeLastArticleId(article) :
    f = open("/home/ubuntu/data/crawling/last_article.txt", 'w')
    print(article)
    f.write(article)
    f.close()
    
# 시작 시간
start = time.time()

# DB에 저장할 Dataframe 설정
column_name = ["article_id","article_category", "article_regtime", "article_editor", "article_press", "article_title",
               "article_thumbnail", "article_content", "article_url", "article_hits"]

# category
sid1 = (101,) # 대분류
sid2 = {'금융': 259, '증권': 258, '산업/재계': 261, '중기/벤처': 771, '부동산': 260, '글로벌 경제': 262, '생활경제': 310, '경제 일반': 263} # 소분류

# date 
end_date = dt.datetime.now(timezone('Asia/Seoul'))
start_date = end_date
# result
results = []

# press
press_list = ['매일경제', '머니투데이', '비즈워치', '서울경제', '아시아경제', '이데일리', '조선비즈', '조세일보', '파이낸셜뉴스', '한국경제', '헤럴드경제',
              '경향신문', '국민일보', '동아일보', '문화일보', '서울신문', '세계일보', '조선일보', '중앙일보', '한계레', '한국일보']

# separator
separator_image = "@@img"
separator_image_end = "@@endimg"
separator_image_desc = "@@imgdesc"
separator_image_desc_end = "@@endimgdesc"
separator_strong = "@@strong"
separator_strong_end = "@@endstrong"

# debug
debug = True

# DB 연결
db_connection_str = 'mysql+pymysql://root:ssafy@j8a509.p.ssafy.io:3306/ssafy_cow_db'
db_connection = create_engine(db_connection_str)

# 데이터 조회 
sql = "select max(article_id) as max_id from article"

df = pd.DataFrame(db_connection.connect().execute(text(sql)))
last_article_id = 0

if df.iloc[0]['max_id'] is not None:
    last_article_id = df.iloc[0]['max_id']

# 마지막으로 저장된 기사 아이디 저장
writeLastArticleId(str(last_article_id))

# 대분류로 반복
for main in sid1:
    if debug: print("sid1", main)
    dates = readArticleTime()
    write_date = ""
    # 소분류로 반복
    for subkey, subval in sid2.items():
        index = 0
        if debug: print("sid2", subval)
        # 소분류별 마지막 기사 시간
        print(subkey," 마지막 기사 시간: ", dates[list(sid2).index(subkey)])
        last_date = dates[list(sid2).index(subkey)]
        # 날짜로 반복
        s_date = start_date
        while s_date <= end_date:
            # 페이지로 반복
            page = 1
            max_page = 1

            while page <= max_page:
                if debug: print("page", page)
                response = urlopen(
                    f'https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2={subval}&sid1={main}&date={s_date.strftime("%Y%m%d")}&page={page}')
                soup = BeautifulSoup(response, "html.parser")
               
                # 페이지 수 구하기
                if page % 10 == 1:
                    page_a_list = soup.find("div", {"class": "paging"}).find_all("a")
                    if page_a_list:
                        last_value = page_a_list[-1].get_text()
                        if last_value == '다음':
                            max_page += 10
                        elif last_value.isdigit():
                            max_page = int(last_value)

                # 현재 페이지 리스트에 있는 기사의 링크 가져오기
                value = soup.find_all("div", {"class": "newsflash_body"})

                for i in value:
                    links = i.find_all("dl", class_=False)
                    
                    # 링크를 반복하며 세부 기사 페이지에서 내용 가져오기
                    for link in links:
                        
                        li = link.find("dt", class_=False).find("a").attrs["href"]  # 뉴스 상세 조회 링크
                        press_name = link.find("span", {"class": "writing"}).get_text()

                        if li is None or (press_name not in press_list):
                            continue

                        try:
                            detail_response = urlopen(li)
                            detail_soup = BeautifulSoup(detail_response, "html.parser")

                            header = detail_soup.find("div", {"class": "media_end_head"})

                            # header가 없으면 continue
                            if header is None:
                                continue

                            article_time = header.find("span", {"class": "media_end_head_info_datestamp_time"})['data-date-time']

                            # 마지막 기사 등록일자 이후 기사만 크롤링
                            if(article_time<=last_date):
                                break

                            detail = {}

                            # 기사 카테고리 (article_category)
                            detail["article_category"] = subkey

                            # 발행일시 (article_regtime)
                            date = header.find("span", {"class": "media_end_head_info_datestamp_time"})['data-date-time']
                            detail["article_regtime"] = date

                            # 기자 (article_editor)
                            reporter = header.find("em", {"class": "media_end_head_journalist_name"})
                            detail["article_editor"] = ""
                            if reporter is not None:
                                detail["article_editor"] = reporter.get_text()

                            # 언론사 (article_press)
                            detail["article_press"] = press_name

                            # 기사 제목 (article_title)
                            detail["article_title"] = header.find("h2").find("span").get_text()

                            # 기사 내용 (article_content) - 소제목, 이미지, 이미지 설명
                            contents = detail_soup.find("div", {"class": "_article_content"})
                            content = ""

                            # print("result_before", contents)
                            br_list = contents.find_all("br")

                            for br in br_list:
                                br.replace_with("@@br")

                            td_list = contents.find_all("td")
                            for td in td_list:
                              if(td.find_all("table")):
                                continue
                              img_desc = td.get_text()
                              if len(img_desc.strip())>0:
                                td.replace_with(separator_image_desc + td.get_text() + separator_image_desc_end)

                            img_list = contents.find_all("img")
                            if len(img_list) != 0 :
                                detail["article_thumbnail"] = img_list[0].get("data-src")

                            for img in img_list:
                                img.replace_with(separator_image + img.get("data-src") + separator_image_end)

                            em_list = contents.find_all("em")
                            for em in em_list:
                                em.replace_with(separator_image_desc + em.get_text() + separator_image_desc_end)

                            strong_list = contents.find_all("strong")
                            for strong in strong_list:
                                strong.replace_with(separator_strong + strong.get_text() + separator_strong_end)

                            b_list = contents.find_all("b")
                            for b in b_list:
                                b.replace_with(separator_strong + b.get_text() + separator_strong_end)

                            tmp_content = contents.get_text().strip()

                            tmp_content = tmp_content.replace("@@imgdesc", "<em>")
                            tmp_content = tmp_content.replace("@@endimgdesc", "</em>")
                            tmp_content = tmp_content.replace("@@img", "<img src='")
                            tmp_content = tmp_content.replace("@@endimg", "' />")
                            tmp_content = tmp_content.replace("@@strong", "<div><strong>")
                            tmp_content = tmp_content.replace("@@endstrong", "</strong></div>")
                            tmp_content = tmp_content.replace("@@br", "<br />")

                            detail["article_content"] = tmp_content
                            # 원본 링크 (article_url)
                            original = header.find("a", {"class": "media_end_head_origin_link"})['href']
                            detail["article_url"] = original

                            # 조회수
                            detail["article_hits"] = 0

                            # 기사 아이디
                            last_article_id+=1
                            detail["article_id"] = last_article_id

                            results.append(detail)
                            index+=1
                        except HTTPError as e:
                            err = e.read()
                            code = e.getcode()
                            print(code) 
                page += 1
            # 하루 더해서 다음날로 넘어가기
            s_date += dt.timedelta(days=1)
            print(s_date)
        print("길이: ",len(results))
        if index > 0:
            print(subkey," 마지막 기사 시간: ", results[len(results)-index]['article_regtime'], "\n")
            write_date+=(results[len(results)-index]['article_regtime']+"\n")
        else:
            print(subkey," 마지막 기사 시간: ", last_date, "\n")
            write_date+=(last_date+"\n")
    # 마지막 기사 시간 저장
    writeArticleTime(write_date)
        
# 결과 출력
print("전체 길이", len(results))

# 뉴스 데이터 Dataframe 설정
news_df = pd.DataFrame(results, columns=column_name)

# 인덱스 재정렬
news_df = news_df.sort_index(ascending=False)
news_df = news_df.reset_index(drop=True)
list = news_df[["article_id"]].values.tolist()
list.reverse()
news_df[["article_id"]]=list

# 종료 시간
end = time.time()

print(f'소요 시간 {end - start}초')

# # DB로 저장
# db_connection_str = 'mysql+pymysql://root:ssafy@j8a509.p.ssafy.io:3306/ssafy_cow_db'
# db_connection = create_engine(db_connection_str)
# news_df.to_sql(name='article', con=db_connection, if_exists='append',index=False)  

# HDFS 에 데이터 넣기
newsdata.put_data(news_df)

