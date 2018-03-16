FROM openjdk:8-jre

VOLUME /tmp

ADD Bot-0.0.1-SNAPSHOT.jar bot.jar

RUN bash -c 'touch /bot.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bot.jar"]
