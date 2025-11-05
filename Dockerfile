## Multi-stage Dockerfile for Spring Boot (Java 17) using Maven
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app

# Copy everything and build (use mvnw if present)
COPY . .
RUN chmod +x mvnw || true
RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the packaged jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
