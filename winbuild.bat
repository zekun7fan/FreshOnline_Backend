./mvnw package
docker build -t springio/gs-spring-boot-docker .
docker run  -p 8080:8080 -p 3306:3306 --name freshonline springio/gs-spring-boot-docker