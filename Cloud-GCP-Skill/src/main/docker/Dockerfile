FROM openjdk:8-jre

VOLUME /tmp

ADD Cloud-Aws-Skill.jar cloud-aws-skill.jar

RUN bash -c 'touch /cloud-aws-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cloud-aws-skill.jar"]
