FROM openjdk:8-jre

VOLUME /tmp

ADD GaaS.jar git-sync-bot.jar

RUN bash -c 'touch /git-sync-bot.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/git-sync-bot.jar"]
