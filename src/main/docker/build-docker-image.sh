#!/bin/bash

VERSION=@project.version@-@buildNumber@
IMAGE_NAME=@project.name@

docker build \
    -t ${IMAGE_NAME}:${VERSION} \
    -t ${IMAGE_NAME}:latest \
    .
