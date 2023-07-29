# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build

COPY . .
RUN mvn clean package -DskipTests -Dmaven

# Run stage	
FROM openjdk:17-slim

COPY --from=build /target/souvenir-1.0-SNAPSHOT-jar-with-dependencies.jar storage.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","storage.jar"]