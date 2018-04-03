FROM openjdk:8-jre

VOLUME /tmp

ADD Notification-Slack-Skill-0.0.1-SNAPSHOT.jar notification-slack-skill.jar

RUN bash -c 'touch /notification-slack-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/notification-slack-skill.jar"]
