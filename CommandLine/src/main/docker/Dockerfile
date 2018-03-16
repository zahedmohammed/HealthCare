FROM openjdk:8-jre

VOLUME /tmp

ADD CommandLine-0.0.1-SNAPSHOT.jar cli.jar

RUN bash -c 'touch /cli.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cli.jar"]
