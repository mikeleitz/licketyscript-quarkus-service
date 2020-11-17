# lsc/licketyscript-quarkus-service

This is the service for lickety-script. It's written in Quarkus.

## Getting Started

Download links:

SSH clone URL: ssh://git@git.jetbrains.space/leadtechnologist/lsc/licketyscript-quarkus-service.git

HTTPS clone URL: https://git.jetbrains.space/leadtechnologist/lsc/licketyscript-quarkus-service.git



These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites

What things you need to install the software and how to install them.

```
Examples
```

## Deployment

Add additional notes about how to deploy this on a production system.

## Resources

Add links to external resources for this project, such as CI server, bug tracker, etc.


# Build quarkus native with build server.

## Install graalvm and deps to linux 64bit

```
git clone https://git.jetbrains.space/leadtechnologist/lsc/licketyscript-quarkus-service.git

./gradlew clean build -Dquarkus.package.type=native

docker build -f src/main/docker/Dockerfile.native -t licketyscript-quarkus-service:latest .

docker run -i --rm -p 8080:8080 licketyscript-quarkus-service:latest

docker login leadtechnologist.registry.jetbrains.space -u leitz

docker tag licketyscript-quarkus-service:latest leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service:latest 

docker push leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service:latest 
```

## Manually mirror docker image from space -> gcloud repo

```
docker pull leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service:latest

docker tag leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service:latest gcr.io/lickety-script/licketyscript-quarkus-service:latest

docker push gcr.io/lickety-script/licketyscript-quarkus-service:latest
```
