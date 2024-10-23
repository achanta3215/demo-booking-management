# Use an appropriate base image with JDK 21
FROM openjdk:21-jdk-slim AS build

WORKDIR /app

# Copy the Maven wrapper and POM file
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Install Maven dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Use a smaller image for the runtime
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
