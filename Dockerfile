FROM azul/zulu-openjdk:21-jdk AS builder
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle .
COPY src src
RUN chmod +x gradlew
RUN ./gradlew bootJar -x test

FROM azul/zulu-openjdk:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
