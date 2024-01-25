FROM maven:3.9.6-eclipse-temurin-21-alpine as builder
WORKDIR /opt/app
COPY mvnw pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-alpine
WORKDIR /opt/app
ARG JAR_FILE=/opt/app/target/*.jar
EXPOSE 8080
COPY --from=builder ${JAR_FILE} /opt/app/*.jar
ENTRYPOINT ["java","-jar","/opt/app/*.jar"]