FROM openjdk:8-jre

VOLUME /tmp

ADD Bot.jar bot.jar

RUN bash -c 'touch /bot.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bot.jar"]
