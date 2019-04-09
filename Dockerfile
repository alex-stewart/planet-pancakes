FROM openjdk:8-jdk-alpine
VOLUME /resources
COPY target/planet-pancakes.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]