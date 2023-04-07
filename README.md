![소귀경](/docs/image/header.png)

# 소귀경 (소 귀에 경제읽기)

- 실시간 경제기사에 따른 경제 trend 파악 및 경제 스터디 서비스
- 기간 2023.02.20 ~ 04.07 (7주)
- 삼성청년SW아카데미 SSAFY 8기 2학기 특화 프로젝트
- TEAM 난!쥉이조 : 엄희정(팀장), 서지윤, 손승환, 민동주, 신도연
- [소 귀에 경제 읽기 UCC](https://www.youtube.com/watch?v=IEKZ6vcX720)

## 목차

1. [기획 배경](#기획-배경)
2. [소귀경 소개](#소귀경-소개)
3. [주요 기능](#주요-기능)
4. [주요 기술](#주요-기술)
5. [팀원 역할](#팀원-역할)
6. [프로젝트 구조](#프로젝트-구조)
7. [산출물](#산출물)
8. [서비스 화면](#서비스-화면)

## 기획 배경

경제는 실제로 우리가 살아가는 모든 것이라고 생각합니다.

하지만, 대부분의 사람들이 경제에 대해서는 잘 모르고 공부를 시작하는 것도 어려워 한다고 합니다. 실제 경제 기사를 많이 읽는 것이 경제 공부의 시작이라고 하는데요. 처음 경제 기사를 읽는 사용자 입장에서는 어렵게 다가와 금방 포기해버린다고 합니다.

저희는 이러한 점에 초점을 맞춰 사용자에게 경제 기사를 쉽게 제공하여 사용자의 경제 지식이 향상될 수 있도록 기획하였습니다.

## 소귀경 소개

‘소 귀에 경제읽기’ 이하 “소귀경”은 실시간으로 올라오는 경제 기사를 제공하고 WordCloud를 통해 현 경제의 trend를 확인할 수 있습니다.

또한, 경제 기사 안에 있는 경제 용어의 정의를 손쉽게 확인하여 경제 기사를 읽는 데 도움을 주고 경제 기사 글을 인용하여 memo 작성 및 경제 용어 Quiz를 통해 사용자의 경제 지식 수준이 향상될 수 있는 기능을 제공하고 있습니다.

사용자가 읽은 기사, 경제 용어 Quiz의 데이터를 수집하여 그래프를 통해 시각화하여 사용자가 자신의 경제 지식 수준을 쉽게 확인할 수 있도록 하였습니다.

## 주요 기능

### 메인 페이지

- **최신 트렌드** WordCloud를 통해 현 경제기사의 trend 확인 가능
- **인기 뉴스** 사용자들이 가장 많이 읽은 경제 기사 확인 가능
- **카테고리별 뉴스** 최신 또는 인기순으로 카테고리별 경제 기사 확인 가능

### 뉴스 목록 조회

- 최신, 인기순 및 기사 카테고리로 필터링한 전체 뉴스 조회
- 키워드를 입력해 뉴스 검색 가능

### 뉴스 상세페이지

- **경제 기사 조회**
  - 경제 기사 제목, 내용, 작성일, 카테고리, 언론사 조회
  - 경제 기사의 원본 Link로 이동 가능
  - 경제 기사 내 밑줄 친 경제 용어 클릭 시 용어의 정의 조회
  - 카카오톡으로 기사 공유 가능
- **관련 기사**
  - 현재 읽고 있는 경제 기사와 관련된 경제 기사 추천
  - PySpark의 TF-IDF 계산, cosine 유사도 계산을 통해 관련 기사 선정
- **기사 메모**
  - 각 기사에 대한 메모 등록, 수정, 삭제 가능
  - 경제 기사의 일부를 드래그하여 메모에 인용문 추가 가능

### 오늘의 Quiz

- 사용자가 일주일 내에 읽었던 경제 기사를 기반으로 하는 Quiz
- 경제 기사 안에 있던 경제 용어를 랜덤으로 선택해 문제 출제
- 4지선다로 정답을 제외한 선택지는 chatGPT를 사용해 구성

### 마이페이지

- **나의 레벨**
  - 현재 나의 경제 지식 수준을 Level을 통하여 확인 가능
  - 경험치에 따라 F ~ S까지 총 6단계의 Level로 구성
  - 경제 기사를 1개 읽을 때마다 +5 및 Quiz 통과 시 +100의 경험치 획득
- **나의 정보 그래프**
  - 사용자 활동에 대한 3개의 그래프 시각화 제공
  - 최근 6개월 동안 읽은 기사 수, 1년 동안 읽은 기사의 카테고리, 1달 동안 Quiz에서 맞춘 경제용어 카테고리
- **나의 메모**
  - 사용자가 작성한 메모를 기사별로 조회 가능
  - 기사 제목, 내용, 인용문 등으로 검색 가능

## 주요 기술

### Backend

- <img src="https://img.shields.io/badge/Spring Boot 2.7.9-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=&logoColor=white"/>
- <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white"/> <img src="https://img.shields.io/badge/OAuth-000000?style=flat-square&logo=&logoColor=white"/> <img src="https://img.shields.io/badge/JWT-000000?style=flat-square&logo=&logoColor=white"/>
- <img src="https://img.shields.io/badge/Hadoop-66CCFF?style=flat-square&logo=apachehadoop&logoColor=black"/> <img src="https://img.shields.io/badge/Pyspark-E25A1C?style=flat-square&logo=apachespark&logoColor=white"/>
- <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/>
- <img src="https://img.shields.io/badge/Intellij IDEA-000000?style=flat-square&logo=Intellij IDEA&logoColor=white"/>

### Frontend

- <img src="https://img.shields.io/badge/Vue.js 2.6.14-4FC08D?style=flat-square&logo=Vue.js&logoColor=white"/> <img src="https://img.shields.io/badge/Vuex-4FC08D?style=flat-square&logo=&logoColor=white"/>
- <img src="https://img.shields.io/badge/Vuetify 2.6.14-1867C0?style=flat-square&logo=Vuetify&logoColor=white"/> <img src="https://img.shields.io/badge/chartjs-8041D9?style=flat-square&logo=&logoColor=white"/>
- <img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=flat-square&logo=Visual Studio Code&logoColor=white"/>

### Database

- <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/HDFS-DC382D?style=flat-square&logo=&logoColor=white"/>

### DevOps

- <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat-square&logo=Amazon EC2&logoColor=white"/> <img src="https://img.shields.io/badge/NGINX-009639?style=flat-square&logo=NGINX&logoColor=white"/> <img src="https://img.shields.io/badge/SSL-0054FF?style=flat-square&logo=&logoColor=white"/>
- <img src="https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=Jenkins&logoColor=white"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"/>

### Tools

- <img src="https://img.shields.io/badge/GitLab-FC6D26?style=flat-square&logo=GitLab&logoColor=white"/> <img src="https://img.shields.io/badge/Jira-0052CC?style=flat-square&logo=Jira Software&logoColor=white"/>
- <img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/>
- <img src="https://img.shields.io/badge/Discord-5865F2?style=flat-square&logo=Discord&logoColor=white"/> <img src="https://img.shields.io/badge/Mattermost-0058CC?style=flat-square&logo=Mattermost&logoColor=white"/>

## 팀원 역할

![TEAM](/docs/image/team.png)

## 프로젝트 구조

### ERD

![ERD](/docs/image/erd.png)

### Architecture

![Architecture](/docs/image/architecture.png)

## 산출물

- [와이어프레임](https://www.figma.com/file/kxRoFFoYETbdrFJGrqAqJj/%EC%86%8C%EA%B7%80%EA%B2%BD-%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84?node-id=0-1&t=mFMMCkOs3ytm6m7t-0)
- [기능명세서](https://mercury-sole-13c.notion.site/5bf73261a066404d9dc038a88e5e2348)
- [DB 설계서](https://mercury-sole-13c.notion.site/DB-2f2f7119771b4d31a374d6f0ca0374e6)
- [API 명세서](https://mercury-sole-13c.notion.site/API-da736710b9644a75b92a38f8834eae3c)
- [포팅 매뉴얼](https://lab.ssafy.com/s08-bigdata-dist-sub2/S08P22A509/-/tree/feat/readme/exec)
- [Convention](https://mercury-sole-13c.notion.site/Convention-76c19c3ddc414066afedcff496085485)

## 서비스 화면

### Info : 서비스 소개

<img src="/docs/screen/info_page.gif" width="300"/>

- 로그인을 하지 않은 채 '소귀경'에 접속했을때 보여지는 페이지
- 소귀경 서비스 전반에 대한 설명을 제공
- 아래로 드래그해서 페이지 이동 가능

### Home : 트렌드, 인기뉴스, 카테고리별 뉴스

<img src="/docs/screen/home_page.gif" width="300"/>

- '소귀경'의 첫 페이지로 최신 트렌드, 인기뉴스, 카테고리별 뉴스 제공
- 최신 트렌드는 1시간 내 발행된 기사 중 자주 등장한 단어를 워드 클라우드로 제공
- 인기 뉴스는 당일 발행된 기사 중 조회수가 높은 10개 제공
- 카테고리별 뉴스는 당일 발행된 기사를 카테고리별로 최신순, 인기순으로 조회 가능

### News : 경제 뉴스 목록

<img src="/docs/screen/news_list.gif" width="300"/>

- 전체 뉴스 리스트를 카테고리별로, 최신순 또는 인기순으로 제공
- 무한 스크롤로 전체 기사 조회 가능

### News Detail : 뉴스 상세페이지

<img src="/docs/screen/news_detail.gif" width="300"/>

- 각 뉴스에 대한 전체 내용을 볼 수 있는 페이지
- 카테고리, 언론사, 전체 내용, 기자, 원본URL 제공
- 기사 내용을 보는 글씨 크기 조절 가능
- 기사 내용 아래로 스크롤 이동 시, 기사 읽음 처리 창 하단에 활성화
- 밑줄 친 단어 클릭 시, 단어 설명 모달 활성화
- 해당 기사의 관련 기사 6개 제공

<img src="/docs/screen/news_detail_2.gif" width="300"/>

- 메모 등록, 수정, 삭제, 공개여부 변경 가능
- 기사 내용을 드래그해 메모 내 인용문으로 추가 가능
- 카카오톡으로 기사 공유 가능

### Search : 키워드 검색

<img src="/docs/screen/search_page.gif" width="300"/>

- 키워드로 기사 검색 가능
- 카테고리별로, 최신순 또는 인기순으로 볼 수 있으며, 무한 스크롤로 구현

### Quiz : 경제 용어 퀴즈

<img src="/docs/screen/quiz_page.gif" width="300"/>

- 읽었던 기사에 대한 경제 용어 퀴즈 풀이 가능
- 총 7문제로 5문제 이상 풀면 경험치 + 100
- 문제당 제한 시간은 15초로, 4개의 보기 중 답을 선택

### MyPage : 로그인 및 나의 정보

<img src="/docs/screen/mypage.gif" width="300"/>

- 나의 레벨과 경험치를 조회 가능
- 소귀경의 전체 레벨 구조 조회 가능
- 내가 읽은 기사 수, 읽은 기사의 카테고리, 경제 용어의 카테고리를 그래프로 제공
- 사용자가 작성한 메모를 조회, 공개여부 수정, 삭제 가능
- 메모를 전체, 기사 제목, 내용, 메모 내용, 인용구로 검색 가능
- 로그인된 상태라면 로그아웃 가능
- 비로그인된 상태라면 마이페이지 대신 로그인 버튼 활성화
