### Multi-stage Dockerfile for Spring Boot (Java 17)
### Builds the app with the included Maven wrapper and packages a runnable JAR

FROM eclipse-temurin:17-alpine AS builder
WORKDIR /workspace

# copy maven wrapper and settings first for better layer caching
COPY mvnw pom.xml ./
COPY .mvn .mvn

# copy sources
COPY src src

# Ensure wrapper is executable and build
RUN chmod +x mvnw && ./mvnw -B -DskipTests package -Dmaven.repo.local=/root/.m2/repository

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# copy jar from builder stage
COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
