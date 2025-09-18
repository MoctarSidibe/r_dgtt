# Use Maven image to build
FROM maven:3.9.5-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use OpenJDK 17 runtime image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/autres-service-1.0.0.jar app.jar

# Expose port
EXPOSE 8084

# Run the application
CMD ["java", "-jar", "app.jar"]
