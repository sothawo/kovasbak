FROM frolvlad/alpine-oraclejdk8:8.131.11-slim

LABEL maintainer="<pj.meisch@sothawo.com>"

RUN apk add --no-cache \
    bash \
  && addgroup kafka \
  && adduser -S -D kafka -G kafka

USER kafka

ADD ["kafka_2.11-0.11.0.0.tgz", "/"]

WORKDIR kafka_2.11-0.11.0.0

