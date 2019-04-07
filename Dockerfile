FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/planet-pancakes.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]