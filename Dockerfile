FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /target/money-0.0.1-SNAPSHOT.jar aimoney.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","/aimoney.jar"]