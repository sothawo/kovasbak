FROM frolvlad/alpine-oraclejdk8:8.131.11-slim

LABEL maintainer="<pj.meisch@sothawo.com>"

RUN apk add --no-cache \
    bash \
  && addgroup kovasbak \
  && adduser -S -D kovasbak -G kovasbak

USER kovasbak

ADD ["@project.build.finalName@.jar", "./"]

ENTRYPOINT ["java", "-jar", "@project.build.finalName@.jar"]

EXPOSE 8080

