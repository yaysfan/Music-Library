# 1. Build Stage
FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# yt-dlp와 ffmpeg 설치
RUN apt-get update && \
    apt-get install -y python3 python3-pip ffmpeg && \
    pip3 install --upgrade yt-dlp

# 2. Run Stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/music-0.0.1-SNAPSHOT.jar ./app.jar

# yt-dlp와 ffmpeg를 Run Stage에도 설치 (Builder Stage에서 복사)
COPY --from=builder /usr/local/bin/yt-dlp /usr/local/bin/yt-dlp
RUN apt-get update && apt-get install -y ffmpeg python3 python3-pip

# PATH에 포함
ENV PATH="/usr/local/bin:${PATH}"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
