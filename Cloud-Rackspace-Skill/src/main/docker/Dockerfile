FROM openjdk:8-jre

VOLUME /tmp

ADD Cloud-Rackspace-Skill.jar cloud-rackspace-skill.jar

RUN bash -c 'touch /cloud-rackspace-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cloud-rackspace-skill.jar"]
