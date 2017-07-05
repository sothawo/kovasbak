# kovasbak

a simple chat application built with
 * Kotlin
 * Vaadin
 * Spring Boot
 * Apache Kafka
 
the project's name is built from the first letters of the language/tools/product used to build it.

Just a demo to show how the language, frameworks and tools work together. 

[detailed description here](https://www.sothawo.com/2017/07/a-simple-web-based-chat-application-built-with-kotlin-vaadin-spring-boot-and-apache-kafka/)

## the docker directory

this directory contain the scripts that are necessary to run all components in docker, the files are for docker 
running in swarm mode. To get it going:

    cd docker
    cd kafkabase
    docker build -t kovasbak-kafkabase .
    cd ../zookeeper
    docker build -t kovasbak-zookeeper .
    cd ../kafkabroker
    docker build -t kovasbak-kafkabroker .
    cd ../..
    mvn package 
    cd target
    sh build-docker-images.sh
    cd ../docker
    docker stack deploy --compose-file=docker-compose.yml kovasbak

