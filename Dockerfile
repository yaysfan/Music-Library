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

# yt-dlp 직접 다운로드
RUN curl -L https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -o /usr/local/bin/yt-dlp \
    && chmod +x /usr/local/bin/yt-dlp

# ffmpeg 설치
RUN apt-get update && apt-get install -y ffmpeg

# yt-dlp가 /usr/local/bin에 설치되므로 PATH에 추가
ENV PATH="/usr/local/bin:${PATH}"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
