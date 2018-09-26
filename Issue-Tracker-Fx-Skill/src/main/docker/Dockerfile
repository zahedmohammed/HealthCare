FROM openjdk:8-jre

VOLUME /tmp

ADD Issue-Tracker-Fx-Skill-0.0.1-SNAPSHOT.jar issue-tracker-fx-skill.jar

RUN bash -c 'touch /issue-tracker-fx-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/issue-tracker-fx-skill.jar"]
