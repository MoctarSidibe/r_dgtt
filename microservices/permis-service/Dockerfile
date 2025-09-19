FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the entire project
COPY . .

# Change to permis service directory
WORKDIR /app/microservices/permis-service

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/permis-service-1.0.0.jar"]