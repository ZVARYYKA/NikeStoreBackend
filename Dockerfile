
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target /app

EXPOSE 8080

CMD ["java", "-jar", "NikeStoreBackend-0.0.1-SNAPSHOT.jar"]
