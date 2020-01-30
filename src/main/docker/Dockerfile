FROM openjdk:8-jre

LABEL maintainer="<pj.meisch@sothawo.com>"

RUN addgroup kovasbak \
  && adduser --disabled-login --no-create-home --disabled-login --ingroup kovasbak kovasbak

USER kovasbak

ADD ["@project.build.finalName@.jar", "./"]

ENTRYPOINT ["java", "-jar", "@project.build.finalName@.jar"]

EXPOSE 8080
