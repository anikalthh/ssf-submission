FROM maven:3.9-eclipse-temurin-21

WORKDIR /app 

COPY mvnw . 
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
COPY events.json .

RUN mvn package -Dmaven.test.skip=true

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/eventmanagement-0.0.1-SNAPSHOT.jar