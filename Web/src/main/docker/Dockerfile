FROM openjdk:8-jre

VOLUME /tmp

ADD Web.jar fxcp.jar

RUN bash -c 'touch /fxcp.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/fxcp.jar"]
