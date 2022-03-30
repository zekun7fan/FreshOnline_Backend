#!/bin/bash
docker stop freshonline
docker rm freshonline
./mvnw package
docker build -t springio/gs-spring-boot-docker .
docker run -p 8080:8080 --name freshonline springio/gs-spring-boot-docker -d


