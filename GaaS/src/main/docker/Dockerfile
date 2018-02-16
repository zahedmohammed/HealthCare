FROM openjdk:8-jre

VOLUME /tmp

ADD GaaS.jar git-sync-aas.jar

RUN bash -c 'touch /git-sync-aas.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/git-sync-aas.jar"]
