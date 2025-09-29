# 1. Build Stage
FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# yt-dlp와 ffmpeg 설치 (시스템 패키지)
RUN apt-get update && \
    apt-get install -y yt-dlp ffmpeg python3

# 2. Run Stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/music-0.0.1-SNAPSHOT.jar ./app.jar

# Run Stage에서도 yt-dlp와 ffmpeg 설치
RUN apt-get update && \
    apt-get install -y yt-dlp ffmpeg python3

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
