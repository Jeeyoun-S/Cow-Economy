# 소귀경 포팅 매뉴얼

## 목차

- [소귀경 포팅 매뉴얼](#소귀경-포팅-매뉴얼)
  - [목차](#목차)
  - [프로젝트 기술 스택](#프로젝트-기술-스택)
  - [EC2 세팅](#ec2-세팅)
    - [Nginx \& Letsencrypt 설치](#nginx--letsencrypt-설치)
    - [Docker 설치](#docker-설치)
    - [MySQL 환경설정](#mysql-환경설정)
  - [프로젝트 빌드 및 배포](#프로젝트-빌드-및-배포)
    - [프론트엔드 도커 파일 작성](#프론트엔드-도커-파일-작성)
    - [프론트엔드 배포](#프론트엔드-배포)
    - [백엔드 도커 파일 작성](#백엔드-도커-파일-작성)
    - [백엔드 배포](#백엔드-배포)
  - [환경변수 설정](#환경변수-설정)
    - [프론트엔드 .env 파일 설정](#프론트엔드-env-파일-설정)
    - [백엔드 application.yml 설정](#백엔드-applicationyml-설정)
  - [Hadoop, Crontab 설정](#hadoop-crontab-설정)
    - [hadoop 설치, 환경 설정](#hadoop-설치-환경-설정)
    - [Cron 설치 및 환경설정](#cron-설치-및-환경설정)
  - [배포 자동화를 위한 Jenkins 설치](#배포-자동화를-위한-jenkins-설치)
    - [젠킨스 설치](#젠킨스-설치)
    - [젠킨스 Git 연동](#젠킨스-git-연동)
    - [프로젝트 빌드 시 실행되는 파이프라인](#프로젝트-빌드-시-실행되는-파이프라인)
  - [외부 서비스](#외부-서비스)
    - [OpenAI](#openai)
    - [KakaoTalk](#kakaotalk)

## 프로젝트 기술 스택

- [BE] Spring Boot 2.7.9
- [BE] JDK zulu 11.0.17
- [Data] Python 3.8.10
- [Data] Hadoop 3.3.1
- [Data] Pyspark 3.3.2
- [FE] Vue 2.6.14
- [FE] Vuetify 2.6.14
- [DB] MySQL 8.0.32
- [Infra] Ubuntu 20.04
- [Infra] Docker 23.0.1
- [Infra] Jenkins 2.396
- [Infra] NginX 1.18.0

## EC2 세팅

### Nginx & Letsencrypt 설치

```bash
Nginx는 웹 서버 프로그램으로 HTTP로서의 역할 및 리버스 프록시/로드 밸런서 역할을 수행한다.
Letsencrypt는 Http → Https가 되기 위해서는 SSL 인증서가 필요한데 기본적으로 SSL은 매우 비싸다.
따라서 Letsencrypt라는 무료 SSL 인증서를 통하여 보안을 강화한다.

# Nginx 설치
sudo apt-get update
sudo apt-get install nginx
sudo nginx -v
sudo systemctl stop nginx

# SSL 인증
sudo apt-get install letsencrypt
sudo letsencrypt certonly --stadalone -d j8a509.p.ssafy.io
cd /etc/letsencrypt/live/j8a509.p.ssafy.io # root 계정으로..
cd /etc/nginx/sites-available
sudo vim nginx.conf

# nginx.conf 작성
server{
    location / {
        proxy_pass http://localhost:3000;
    }

    location /api {
        proxy_pass http://localhost:8080;
    }

		# 워드클라우드 이미지를 불러오기 위한 경로
    location /wordcloud {
        alias /home/ubuntu/coweconomy_store/wordcloud/;
        expires -1;
   }
   listen 443 ssl; # managed by Certbot
    # 도메인 이름을 써줘야함
    ssl_certificate /etc/letsencrypt/live/j8a509.p.ssafy.io/fullchain.pem; # managed by Certbot
    # 도메인 이름을 써줘야함
    ssl_certificate_key /etc/letsencrypt/live/j8a509.p.ssafy.io/privkey.pem; # managed by Certbot
    # include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    # ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
}

server {
    # 도메인 이름을 입력
    if ($host = j8a509.p.ssafy.io) {
        return 301 https://$host$request_uri;
    } # managed by Certbot
}

# 파일 연동 및 테스트
sudo ln -s /etc/nginx/sites-available/nginx.conf/etc/nginx/sites-enabled/nginx.conf
sudo nginx -t

# Nginx 재시작
sudo systemctl restart nginx

#Nginx 상태 확인
sudo systemctl status nginx
```

### Docker 설치

```bash
# 패키지 업데이트
sudo apt-get update

# 패키지 설치
sudo apt-get install \
ca-certificates \
curl \
gnupg \
lsb-release

# Docker의 Official GPG Key 를 등록
curl -fsSL [https://download.docker.com/linux/ubuntu/gpg](https://download.docker.com/linux/ubuntu/gpg) | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg


# docker 엔진 설치
sudo apt-get install docker-ce docker-ce-cli containerd.io
docker --version
```

### MySQL 환경설정

- EC2에서 MySQL에 접속하기

```bash
sudo docker exec -it mysql /bin/bash
```

- bash에 접속하면 mysql 접속 명령어 입력

```bash
mysql -u root -p
```

- root 비밀 번호 변경

```bash
ALTER user 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '변경하고 싶은 패스워드';
flush privileges;
```

## 프로젝트 빌드 및 배포

### 프론트엔드 도커 파일 작성

```bash
FROM nginx:stable-alpine
FROM node:18-alpine

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY ./ ./

EXPOSE 3000

CMD ["npm", "run", "serve"]
```

### 프론트엔드 배포

```bash
#이미지 생성(frontend 경로 내 실행)
sudo docker build -t jenkins-coweconomy-client ./

#컨테이너 생성
sudo docker run -d --name jenkins-coweconomy-client -v /home/ubuntu/coweconomy_store:/usr/src/app/public/coweconomy -p 3000:3000 jenkins-coweconomy-client
```

### 백엔드 도커 파일 작성

```bash
# 빌드를 위한 JAVA JDK 설정
FROM openjdk:11-jdk

# 빌드 변수 설정
ARG JAR_FILE=build/libs/CowEconomy-0.0.1-SNAPSHOT.jar

# 파일 복사
COPY ${JAR_FILE} app.jar

# 프로젝트 파일 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### 백엔드 배포

```bash
#이미지 생성(backend 경로 내 실행)
sudo docker build -t jenkins-coweconomy-server ./

#컨테이너 생성
sudo docker run -d --name jenkins-coweconomy-server -v /home/ubuntu/coweconomy_store:/var/lib/coweconomy -p 8080:8080 jenkins-coweconomy-server
```

## 환경변수 설정

### 프론트엔드 .env 파일 설정

```bash
# .env 파일 설정하여 보안 강화

VUE_APP_API_BASE_URL=https://j8a509.p.ssafy.io/api
VUE_APP_BASE_URL=https://j8a509.p.ssafy.io

VUE_APP_WORD_CLOUD_URL=https://j8a509.p.ssafy.io/wordcloud/word_cloud.png

VUE_APP_KAKAO_REST_API_KEY={ 발급받은 Kakao API Key }
VUE_APP_KAKAO_REDIRECT_URI=https://j8a509.p.ssafy.io/my-page
```

### 백엔드 application.yml 설정

```bash
build:
  date: '@build.date@'
jwt:
  expiration: 1296000000
  expirationRefresh: 1296000000
  secret1: { 키 값 }
  secret2: { 키 값 }
  kakaoTokenRequestUrl: https://kauth.kakao.com/oauth/token
  kakaoUserInfoRequestUrl: https://kapi.kakao.com/v2/user/me
  clientId: { 아이디 값 }
	redirectUri: https://j8a509.p.ssafy.io/my-page     #카카오 developer에서 설정한 리다이렉트url
logging:
  file:
    name: ./cow-economy-web.log
  level:
    com:
      samsung:
        security: DEBUG
    org:
      apache:
        tiles: INFO
      springframework:
        web: DEBUG
        security: DEBUG
    root: INFO
management:
  health:
    db:
      enabled: true
    default:
      enabled: true
    diskspace:
      enabled: true
  servlet:
    context-path: /manage
server:
  address: 0.0.0.0          #외부접속 허용(localhost로 설정하면 외부접근 불가)
  compression:
    enabled: true
    mime-types: application/json,application/xml,text/html,text/xml,text/plain,application/javascript,text/css
  port: 포트번호
  servlet:
    context-path: /api      #context-path 설정을 통해 컨트롤러에서 url 매핑 시 '/api' 생략
    encoding:
      charset: UTF-8
      enabled: true
      force: true
spa:
  default-file: /dist/index.html
spring:
  profiles:  # chatGPT
    include: 키 값 # chatGPT
  data:
    web:
      pageable:
        one-indexed-parameters: true
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
		url: jdbc:mysql://DB주소:DB포트/DB이름?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true
    username: 유저이름
    password: 비밀번호
  devtools:
    livereload:
      enabled: true
  # JPA
  jpa:
    hibernate:
      ddl-auto: update
      naming:
        implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
        physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL57Dialect
        format_sql: true
    show-sql: true
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
    throw-exception-if-no-handler-found: true
  web:
    resources:
      add-mappings: false
      static-locations: classpath:/dist/

springfox:
  documentation:
    swagger:
      use-model-v3: false

# Security OAuth
security:
  oauth2.client:
    registration:
      kakao:
        clientId: { 키 값 }
        clientSecret: { 키 값 }

# chatGPT
openai:
  api-key: { API 키 }
chatgpt:
  api-key: { API 키 }
```

## Hadoop, Crontab 설정

### hadoop 설치, 환경 설정

```bash
sudo apt-get update

# openjdk 설치
sudo apt install openjdk-11-jdk
#java 설치 경로 확인
which java

# ------------ Hadoop ------------
# Hadoop 설정(하둡 실행 시 로그인 필요 -> ssh 설정을 통해 이 단계가 무시됨)
sudo apt-get install openssh-server
sudo apt-get install pdsh
ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
chmod 0600 ~/.ssh/authorized_keys
ssh localhost

# Hadoop 설치
wget https://archive.apache.org/dist/hadoop/common/hadoop-3.1.3/hadoop-3.1.3.tar.gz
tar -xzvf hadoop-3.1.3.tar.gz
mv hadoop-3.1.3 /home/ubuntu/hadoop

# 환경 설정
vi ~/.bashrc
----------------------------
export HADOOP_HOME="/home/ubuntu/hadoop"
export PATH=$PATH:$HADOOP_HOME/bin
export PATH=$PATH:$HADOOP_HOME/sbin
export HADOOP_OPTS="$HADOOP_OPTS -Djava.library.path=/home/ubuntu/hadoop/lib/"
export HADOOP_COMMON_LIB_NATIVE_DIR="/home/ubuntu/hadoop/lib/native/"
export HADOOP_HDFS_HOME=${HADOOP_HOME}
export HADOOP_CLASSPATH=$(hadoop classpath)
----------------------------
source ~/.bashrc

# env.sh 수정
cd /home/ubuntu/hadoop/etc/hadoop
vi hadoop-env.sh
----------------------------
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
----------------------------

# core-site.xml 수정
vi core-site.xml
----------------------------
<configuration>
        <property>
                <name>fs.defaultFS</name>
                <value>hdfs://localhost:9000</value>
        </property>
</configuration>
----------------------------
source ~/.bashrc

# hadoop 실행
cd /home/ubuntu/hadoop/bin
hdfs namenode -format

# hdsf 실행
cd /home/ubuntu/hadoop/sbin
start-dfs.sh

# --------- Spark & PySpark ---------
wget https://dlcdn.apache.org/spark/spark-3.3.2/spark-3.3.2-bin-hadoop3.tgz
tar xvzf spark-3.3.2-bin-hadoop3.tgz
ln -s spark-3.3.2-bin-hadoop3 spark

sudo vi ~/.bashrc
----------------------------
export SPARK_HOME="/home/ubuntu/spark"
----------------------------
source ~/.bashrc

# python 설치
sudo apt-get install python3
python3 -V

# pip3 설치
sudo apt-get install pip3

# pyspark 설치
pip install pyspark
pyspark --version
```

### Cron 설치 및 환경설정

```bash
# cron 설치
sudo apt update -y
sudo apt install -y cron

# 작업 스케줄 등록
crontab -e

0 * * * * /home/ubuntu/data/news_schedule.sh >> /home/ubuntu/data/news_schedule.log

# 예약된 스케줄 확인
crontab -l

# cron 시작
sudo service cron start
sudo service cron status

# cron 실행 로그 확인
cat /var/log/syslog | grep CRON

# news_schedule.sh 작업 스케줄 쉘스크립트 작성
#!/bin/sh
echo "start"
python3 ~/data/crawling/news_crawling.py
python3 ~/data/hadoop/word_mapping.py
python3 ~/data/hadoop/data_main.py
echo "end"

# 쉘스크립트 내 접근파일 인코딩 및 포멧 설정, 접근 권한 설정 필수!!!
```

## 배포 자동화를 위한 Jenkins 설치

### 젠킨스 설치

```bash
#젠킨스 컨테이너 생성
sudo docker run -d --name jenkins -u root --privileged \
-p '9090:8080' \
-v '/home/ubuntu/docker-volume/jenkins:/var/jenkins_home' \
-v '/var/run/docker.sock:/var/run/docker.sock' \
-v '/usr/bin/docker:/usr/bin/docker' \
jenkins/jenkins


# 컨테이너 내부 bash 접근
sudo docker exec -it jenkins bash

# Docker 확인
docker -v

# 컨테이너 생성 후 젠킨스 페이지로 입장 후 비밀번호 입력(http://j8a509.p.ssafy.io:9090)
sudo vi /젠킨스 초기비밀번호 위치
```

### 젠킨스 Git 연동

```bash
1. 젠킨스 페이지 이동하여 Credential 생성
  - Manage Credential >> System, global 선택 >> Global credentials >> Add Credentials

	- Kind: Username with password
  - Username: Gitlab 계정 아이디
  - Password: Gitlab 계정 비밀번호
  - ID: 고유한 Credential 아이디

2. Pipeline 생성
 - 새로운 Item >> Pipeline 선택
 - Build Triggers >> Build when a change is pushed to GitLab >> 고급 >> Secret token 생성
 - GitLab webhook URL, 생성된 Secret token 저장해놓기
 - Giitlab 프로젝트 이동
 - GitLab Settings > Web Hook > URL, Secret Token 넣기 (Web Hook 유발하여 파이프라인 실행)

# Pipeline 스크립트 작성

pipeline{
    agent any
    stages{
        stage('Ready'){
            steps{
                sh "echo 'Ready'"
                git branch: 'develop',
                credentialsId: 'hihejyop',
                url: 'https://lab.ssafy.com/s08-bigdata-dist-sub2/S08P22A509.git'

            }
        }
        stage('Backend Build'){
            steps{
                sh "echo 'Test'"
                dir('backend') {
                    sh 'chmod +x gradlew'
                    sh './gradlew clean build'
                }
        }
    }
        stage('Containers Remove'){
            steps{
                sh "sudo docker rm -f jenkins-coweconomy-server"
                sh "sudo docker rm -f jenkins-coweconomy-client"
            }
            }
        stage('Images Remove'){
            steps{
                sh "sudo docker rmi jenkins-coweconomy-server"
                sh "sudo docker rmi jenkins-coweconomy-client"
            }
        }

        stage('Deploy'){
            steps{
                dir('backend'){
                    sh "sudo docker build -t jenkins-coweconomy-server ./"
                    sh "sudo docker run -d --name jenkins-coweconomy-server -v /home/ubuntu/coweconomy_store:/var/lib/coweconomy -p 8080:8080 jenkins-coweconomy-server"
                }
                dir('frontend'){
                    sh "sudo docker build -t jenkins-coweconomy-client ./"
                    sh "sudo docker run -d --name jenkins-coweconomy-client -v /home/ubuntu/coweconomy_store:/usr/src/app/public/coweconomy -p 3000:3000 jenkins-coweconomy-client"
                }
            }
        }
  }
}
```

### 프로젝트 빌드 시 실행되는 파이프라인

![Pipeline](/exec/image/pipeline.png)

## 외부 서비스

### OpenAI

- [참고 Link] openAI API 공식 문서
- https://platform.openai.com/docs/api-reference/introduction

1. https://chat.openai.com/chat 회원가입
2. openai api 키 발급
3. Backend application.yml 환경설정 파일 내 OpenAI API KEY 추가

```bash
# appilcation.yml
...
spring:
  profiles:  # chatGPT
    include: 키 값 # chatGPT
  data:
...
# chatGPT
openai:
  api-key: API 키
chatgpt:
  api-key: API 키
```

### Kakao Login

- [참고 Link] Kakao Development 공식 문서

1. 애플리케이션 등록
   ![create-kakao-application](/exec/image/create-kakao-application.png)
2. 인가 코드 받기

```bash
kakaoLogin() {
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.VUE_APP_KAKAO_REDIRECT_URI}&response_type=code`
      );
    },
```

3. 카카오 토큰 받기

```bash
async executeToken({ commit }) {
      await getToken(
        ({ data }) => {
          // jwt acces-token localstorage에 저장
          if (data.statusCode == 200) {
            const ACCESS_TOKEN = data.data.accessToken;
            const REFRESH_TOKEN = data.data.refreshToken;

            localStorage.setItem("access-token", ACCESS_TOKEN);
            localStorage.setItem("refresh-token", REFRESH_TOKEN);

            // vuex 로그인 처리
            commit("SET_IS_LOGGED_IN", true);

            // my-page로 이동
            window.location.replace("/my-page");
          } else {
            console.error("토큰 발급 실패");
          }
        },
        (error) => {
          console.error(error);
        }
      );
    },
```

### Kakao Message

1. SDK 추가

```bash
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
```

2. 커스텀 탬플릿으로 공유하기

```bash
initKakaoShare() {
      if (!window.Kakao.isInitialized()) {
        alert("카카오 SDK가 초기화되지 않았습니다.");
        return;
      }

      const templateId = 91976;
      this.$refs.kakaoShareButton.onclick = () => {
        window.Kakao.Link.sendCustom({
          templateId: templateId,
          templateArgs: {
            title: this.cur[0],
            content: this.cur[1],
            imageUrl: this.cur[2],
            linkMobile: `https://j8a509.p.ssafy.io${window.location.pathname}`,
            linkWeb: `https://j8a509.p.ssafy.io${window.location.pathname}`,
          },
        });
      };
```
