FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/at_infnet_springboot-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]