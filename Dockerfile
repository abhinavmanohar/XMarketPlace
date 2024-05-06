# Start with a base image for the build stage
FROM maven:3.8.5-openjdk-17 AS build

# Set the current working directory inside the Docker image
WORKDIR /app

# Copy maven executable to the image
COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests
FROM openjdk:17-oracle

WORKDIR /app
COPY --from=build /app/target/XMarket-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

#COPY target/XMarket-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]