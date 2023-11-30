# Stage 1: Build the application
FROM gradle:7.3.3-jdk11 AS builder

WORKDIR /spring-boot/app

COPY build.gradle .
COPY settings.gradle .
COPY gradle ./gradle
COPY src ./src

RUN gradle clean build -x test

# Stage 2: Create the final lightweight image
FROM openjdk:11-jre-slim

ENV PROJECT_DIR=/spring-boot/app

# Create project dir
RUN mkdir -p $PROJECT_DIR/config

WORKDIR $PROJECT_DIR

COPY --from=builder $PROJECT_DIR/build/libs/*SNAPSHOT.jar app.jar
COPY --from=builder $PROJECT_DIR/src/main/resources/application.properties $PROJECT_DIR/config/application.properties

ENV CONFIG_DIR=$PROJECT_DIR/config/application.properties

CMD ["java", "-jar", "app.jar", "--spring.config.location=${CONFIG_DIR}"]