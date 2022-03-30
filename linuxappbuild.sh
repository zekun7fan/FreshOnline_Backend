#!/bin/bash
docker stop freshonline
docker rm freshonline
./mvnw package
docker build -t springio/gs-spring-boot-docker .
docker run --network host --name freshonline springio/gs-spring-boot-docker -d


