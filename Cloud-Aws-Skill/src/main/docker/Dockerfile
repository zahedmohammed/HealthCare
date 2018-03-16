FROM openjdk:8-jre

VOLUME /tmp

ADD Cloud-Aws-Skill-0.0.1-SNAPSHOT.jar cloud-aws-skill.jar

RUN bash -c 'touch /cloud-aws-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cloud-aws-skill.jar"]
