#!/bin/bash
echo "hello world" //print to screen
docker pull mysql
docker run --network host --name mysql -e MYSQL_ROOT_PASSWORD=  -e \ MYSQL_ALLOW_EMPTY_PASSWORD=TRUE -d mysql
sleep 60
docker exec -i mysql sh -c 'exec mysql -uroot' < initialize.sql
./mvnw package
docker build -t springio/gs-spring-boot-docker .
docker run --network host --name freshonline springio/gs-spring-boot-docker

