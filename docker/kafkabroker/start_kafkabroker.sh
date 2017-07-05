#!/usr/bin/env bash
# use the default start script but unset the broker id to have one generated
bin/kafka-server-start.sh config/server.properties --override broker.id=-1 "$@"
