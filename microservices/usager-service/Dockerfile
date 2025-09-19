FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the entire project
COPY . .

# Change to usager service directory
WORKDIR /app/microservices/usager-service

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/usager-service-1.0.0.jar"]