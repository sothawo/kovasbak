#!/bin/bash

VERSION=@project.version@-@buildNumber@
HLX_IMAGE_NAME=@project.name@

docker build \
    -t ${HLX_IMAGE_NAME}:${VERSION} \
    -t ${HLX_IMAGE_NAME}:latest \
    .
