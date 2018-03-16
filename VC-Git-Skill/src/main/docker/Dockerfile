FROM openjdk:8-jre

VOLUME /tmp

ADD VC-Git-Skill-0.0.1-SNAPSHOT.jar vc-git-skill.jar

RUN bash -c 'touch /vc-git-skill.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/vc-git-skill.jar"]
