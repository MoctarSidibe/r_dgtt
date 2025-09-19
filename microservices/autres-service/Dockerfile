FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the entire project
COPY . .

# Change to autres service directory
WORKDIR /app/microservices/autres-service

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/autres-service-1.0.0.jar"]