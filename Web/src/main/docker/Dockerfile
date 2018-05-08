FROM openjdk:8-jre

VOLUME /tmp

ADD Web-0.0.1-SNAPSHOT.jar control-plane.jar

RUN bash -c 'touch /control-plane.jar'

EXPOSE 8080

ENTRYPOINT ["java","$JAVA_OPTS","-Djava.security.egd=file:/dev/./urandom","-jar","/control-plane.jar"]
