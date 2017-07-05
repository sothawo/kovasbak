FROM kovasbak-kafkabase

LABEL maintainer="<pj.meisch@sothawo.com>"

EXPOSE 9092

COPY ["start_kafkabroker.sh", "./"]

USER root
RUN chmod +x start_kafkabroker.sh
USER kafka

ENTRYPOINT ["./start_kafkabroker.sh"]
