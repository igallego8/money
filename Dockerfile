FROM openjdk:8-jdk-alpine
WORKDIR var/jenkins_home/workspace/aimoney-ic/target/
COPY money-0.0.1-SNAPSHOT.jar aimoney.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","/aimoney.jar"]