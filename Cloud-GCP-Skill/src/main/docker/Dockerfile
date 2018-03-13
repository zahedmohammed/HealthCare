FROM openjdk:8-jre

VOLUME /tmp

ADD Cloud-GCP-Skill.jar cloud-gcp-skill.jar

RUN bash -c 'touch /cloud-gcp-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cloud-gcp-skill.jar"]
