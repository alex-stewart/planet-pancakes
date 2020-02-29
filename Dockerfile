FROM openjdk:8-jdk-alpine
VOLUME /resources
COPY build/libs/planet-pancakes.jar app.jar
ENTRYPOINT java -jar app.jar --island.directory="file:/resources/pp-islands/"