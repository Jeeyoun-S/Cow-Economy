# Docker의 이미지를 가져옴
FROM openjdk:11-jdk-slim

# 빌드 변수 설정
ARG JAR_FILE=build/libs/*.jar

# 파일 복사
COPY ${JAR_FILE} app.jar

# 포트 번호 노출 (기능은 없으나 명시적 코딩을 위해 작성)
EXPOSE 8080

# 프로젝트 파일 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]