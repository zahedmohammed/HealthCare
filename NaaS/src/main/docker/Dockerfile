FROM openjdk:8-jre

VOLUME /tmp

ADD NaaS-0.0.1-SNAPSHOT.jar mail-bot.jar

RUN bash -c 'touch /mail-bot.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mail-bot.jar"]
