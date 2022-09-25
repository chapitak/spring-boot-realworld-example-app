FROM gradle:7.4-jdk-alpine
WORKDIR /app
COPY ./ ./
RUN gradle bootJar
ENTRYPOINT ["java", "-jar", "app.jar"]
