#FROM openjdk:17-jdk-alpine
#
#VOLUME /tmp
#ADD target/tbs-api-0.0.1-SNAPSHOT.jar app.jar
#
#ENTRYPOINT ["java","-jar","app.jar"]
#
#EXPOSE 8081

# Use the official OpenJDK image as a base image
FROM openjdk:11-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/tbs-api-0.0.1-SNAPSHOT.jar /app/tbs-api-0.0.1-SNAPSHOT.jar

# Run the Spring Boot application
CMD ["java", "-jar", "/app/tbs-api-0.0.1-SNAPSHOT.jar"]