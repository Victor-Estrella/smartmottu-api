# Multi-stage build for Spring Boot (Java 17)

FROM eclipse-temurin:17-jdk as builder
WORKDIR /app

# Copy Maven wrapper and pom first for better layer caching
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn/ .mvn/

# Ensure mvnw is executable and fix CRLF line endings (Windows -> Linux)
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Download dependencies
RUN ./mvnw -q -B -DskipTests dependency:go-offline

# Copy source and build
COPY src/ src/
RUN ./mvnw -q -B -DskipTests clean package

# Runtime image
FROM eclipse-temurin:17-jre
ENV JAVA_OPTS="" \
    SPRING_PROFILES_ACTIVE=prod 

WORKDIR /opt/app
EXPOSE 8080

# Copy fat jar
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
