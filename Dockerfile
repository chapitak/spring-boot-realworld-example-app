FROM gradle:7.4-jdk-alpine
WORKDIR /app
COPY ./ ./
RUN gradle clean build --no-daemon
CMD java -jar build/libs/*.jar