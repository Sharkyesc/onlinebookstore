FROM openjdk:21-jdk-slim

COPY /target/demo-0.0.1-SNAPSHOT.jar /app/bookstore.jar

ENTRYPOINT ["java", "-jar", "/app/bookstore.jar"]

EXPOSE 8080
