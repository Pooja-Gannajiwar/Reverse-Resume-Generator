# Use official Java image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8083

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]