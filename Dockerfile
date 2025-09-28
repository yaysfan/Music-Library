# 1. Build Stage: Maven을 사용하여 프로젝트를 빌드합니다.
FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# 2. Run Stage: 빌드된 JAR 파일을 실행하는 최종 이미지를 만듭니다.
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/music-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]