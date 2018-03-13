FROM openjdk:8-jre

VOLUME /tmp

ADD Cloud-Kubernetes-Skill.jar cloud-kubernetes-skill.jar

RUN bash -c 'touch /cloud-kubernetes-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cloud-kubernetes-skill.jar"]
