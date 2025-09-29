# 1. Build Stage
FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests


# 2. Run Stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# 필수 패키지 설치 (Run Stage)
RUN apt-get update && \
    apt-get install -y python3 python3-pip ffmpeg && \
    pip3 install --upgrade yt-dlp

COPY --from=builder /app/target/music-0.0.1-SNAPSHOT.jar ./app.jar

ENV PATH="/usr/local/bin:${PATH}"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
