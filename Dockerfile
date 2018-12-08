FROM maven:3.5.3-jdk-9-slim as builder
RUN apt-get -y update && apt-get install -y build-essential libpng-dev
ADD . /aimoney
LABEL type="temporal"
RUN cd /aimoney && mvn -B clean install


FROM openjdk:9-jdk-slim
RUN apt-get -y update && apt-get install -y curl vim
RUN mkdir /opt/aimoney && mkdir /var/log/aimoney
COPY --from=builder /aimoney/target/aimoney.jar /opt/aimoney/aimoney.jar
COPY --from=builder /aimoney/docker/entrypoint.sh /opt/entrypoint.sh
LABEL type="temporal"
RUN chmod +x /opt/entrypoint.sh
EXPOSE 8091

ENTRYPOINT ["/opt/entrypoint.sh"]