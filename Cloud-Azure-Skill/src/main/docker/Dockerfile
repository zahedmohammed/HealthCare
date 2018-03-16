FROM openjdk:8-jre

VOLUME /tmp

ADD Cloud-Azure-Skill-0.0.1-SNAPSHOT.jar cloud-azure-skill.jar

RUN bash -c 'touch /cloud-azure-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cloud-azure-skill.jar"]
