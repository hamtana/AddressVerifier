# ---------- Build stage ----------
FROM gradle:jdk17 AS build

# Use root here only for building
USER root
WORKDIR /app

# Install required build tools
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        bash \
        git \
    && rm -rf /var/lib/apt/lists/*

# Copy project files (make sure .dockerignore excludes sensitive files)
COPY AddressVerifier /app/AddressVerifier
WORKDIR /app/AddressVerifier

# Optional Gradle cache volume (speed up builds)
VOLUME /home/gradle/.gradle

# Build the Spring Boot JAR
RUN gradle --no-daemon -Dorg.gradle.welcome=never bootJar

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-ubi9-minimal

# Create non-root user
RUN useradd -m spring

WORKDIR /home/deployment

# Copy only the built JAR from the build stage
COPY --from=build /app/AddressVerifier/build/libs/*.jar AddressVerifier.jar

# Give ownership to non-root user
RUN chown -R spring:spring /home/deployment

# Switch to non-root
USER spring

# Expose the Spring Boot port
EXPOSE 8085

# Optional: Java memory limits
ENV _JAVA_OPTIONS="-Xmx256M"

# Run the app
CMD ["java", "-jar", "AddressVerifier.jar"]

