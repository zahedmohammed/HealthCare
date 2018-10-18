FROM openjdk:8-jre

VOLUME /tmp

ADD Web-0.0.1-SNAPSHOT.jar parent.jar

RUN bash -c 'touch /parent.jar'

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /control-plane.jar" ]
