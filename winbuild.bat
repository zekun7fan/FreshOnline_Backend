./mvnw package -d
docker build -t springio/gs-spring-boot-docker .
docker run --name freshonline -p 8080:8080 -d springio/gs-spring-boot-docker