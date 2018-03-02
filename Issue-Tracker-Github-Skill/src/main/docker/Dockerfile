FROM openjdk:8-jre

VOLUME /tmp

ADD Issue-Tracker-Github-Skill.jar issue-tracker-github-skill.jar

RUN bash -c 'touch /issue-tracker-github-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/issue-tracker-github-skill.jar"]
