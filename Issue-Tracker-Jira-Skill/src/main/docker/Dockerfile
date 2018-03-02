FROM openjdk:8-jre

VOLUME /tmp

ADD Issue-Tracker-Jira-Skill.jar issue-tracker-jira-skill.jar

RUN bash -c 'touch /issue-tracker-jira-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/issue-tracker-jira-skill.jar"]
