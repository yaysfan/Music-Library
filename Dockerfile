# 1. Build Stage: Maven 빌드
FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# 2. Run Stage: 최종 이미지
FROM eclipse-temurin:17-jre
WORKDIR /app

# 빌드된 JAR 복사
COPY --from=builder /app/target/music-0.0.1-SNAPSHOT.jar ./app.jar

# yt-dlp와 ffmpeg 설치 (pip 사용)
RUN apt-get update && \
    apt-get install -y python3 python3-pip ffmpeg && \
    pip3 install --upgrade yt-dlp

# yt-dlp가 /usr/local/bin에 설치되므로 PATH에 추가
ENV PATH="/usr/local/bin:${PATH}"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
