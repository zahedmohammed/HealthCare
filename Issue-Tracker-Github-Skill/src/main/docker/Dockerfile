FROM openjdk:8-jre

VOLUME /tmp

ADD Issue-Tracker-Github-Skill-0.0.1-SNAPSHOT.jar issue-tracker-github-skill.jar

RUN bash -c 'touch /issue-tracker-github-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/issue-tracker-github-skill.jar"]
