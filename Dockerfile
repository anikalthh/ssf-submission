FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app 

COPY mvnw . 
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
COPY events.json .

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/eventmanagement-0.0.1-SNAPSHOT.jar eventmanagement-0.0.1-SNAPSHOT.jar 
COPY events.json .

ENTRYPOINT ["java", "-jar", "eventmanagement-0.0.1-SNAPSHOT.jar"]

ENV PORT=8080

EXPOSE ${PORT}
