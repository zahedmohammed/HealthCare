FROM openjdk:8-jre

VOLUME /tmp

ADD Cloud-Azure-Skill.jar cloud-azure-skill.jar

RUN bash -c 'touch /cloud-azure-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cloud-azure-skill.jar"]
