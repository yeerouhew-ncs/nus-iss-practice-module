# Dockerfile for tbs-backend
# Use the official Maven image as a base image
FROM maven:3.8.5-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Download the Maven dependencies
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src ./src

# Build the project and package the JAR file
RUN mvn package -DskipTests

# Use the official OpenJDK image as a base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app 

# Copy the packaged JAR file from the build stage to the current directory in the container
COPY --from=build /app/target/tbs-api-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
