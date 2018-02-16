FROM openjdk:8-jre

VOLUME /tmp

ADD BaaS.jar bot-aas.jar

RUN bash -c 'touch /bot-aas.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bot-aas.jar"]
