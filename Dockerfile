FROM maven:3-openjdk-11-slim

WORKDIR /build
COPY . /build

RUN mvn package test

FROM adoptopenjdk:11-jre-hotspot

WORKDIR /data
COPY --from=0 /build/target/*.jar app.jar
COPY data.csv data.csv

USER 1000:1000
EXPOSE 8080/tcp
CMD ["java", "-jar", "app.jar"]