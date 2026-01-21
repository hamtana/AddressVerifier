# ---------- Build stage ----------
FROM gradle:jdk17 AS build

USER root
WORKDIR /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        bash \
        git \
    && rm -rf /var/lib/apt/lists/*

COPY AddressVerifier /app/AddressVerifier
WORKDIR /app/AddressVerifier

VOLUME /home/gradle/.gradle

RUN gradle --no-daemon -Dorg.gradle.welcome=never bootJar


# ---------- Runtime stage ----------
FROM eclipse-temurin:17-ubi9-minimal

WORKDIR /home/deployment

COPY --from=build /app/AddressVerifier/build/libs/*.jar app.jar

ENV _JAVA_OPTIONS="-Xmx256M"

EXPOSE 8085

CMD ["java", "-jar", "app.jar"]

