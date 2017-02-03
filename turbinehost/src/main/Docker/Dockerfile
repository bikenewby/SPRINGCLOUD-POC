#FROM frolvlad/alpine-oraclejdk8:slim
#241.9MB
#FROM anapsix/alpine-java:latest
#198.6MB
FROM develar/java
#192.6MB
MAINTAINER sudduenk@gmail.com
VOLUME /tmp
ADD turbine-host-1.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]