#!/usr/bin/env bash
docker run --rm --name kovasbak-kafkabroker --network kovasbak -p 9092:9092 kovasbak-kafkabroker --override zookeeper.connect=kovasbak-zookeeper:2181
