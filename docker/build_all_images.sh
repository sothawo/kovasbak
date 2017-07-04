#!/usr/bin/env bash

cd kafkabase
docker build -t kovasbak-kafkabase .
cd -

cd zookeeper
docker build -t kovasbak-zookeeper .
cd -

cd kafkabroker
docker build -t kovasbak-kafkabroker .
cd -
