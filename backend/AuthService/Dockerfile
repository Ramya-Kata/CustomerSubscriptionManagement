# Use the official OpenJDK image as the base image for the Spring Boot app
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file built by Maven into the container
COPY target/AuthService-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port your application will run on (default Spring Boot port is 8080)
EXPOSE 8000

# Command to run the Spring Boot app inside the container
CMD ["java", "-jar", "app.jar"]
