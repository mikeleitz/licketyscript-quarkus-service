## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/centos-quarkus-maven:20.1.0-java11 AS build

COPY src /usr/src/app/src
COPY build.gradle /usr/src/app
COPY gradle.properties /usr/src/app
COPY settings.gradle /usr/src/app
COPY lombok.config /usr/src/app
COPY gradlew /usr/src/app
COPY gradle/ /usr/src/app

USER root
RUN chown -R quarkus /usr/src/app

USER quarkus

RUN cd /usr/src/app && gradle build -Dquarkus.package.type=native

## Stage 2 : create the docker final image
FROM registry.access.redhat.com/ubi8/ubi-minimal
WORKDIR /work/
COPY --from=build /usr/src/app/target/*-runner /work/application
RUN chmod 775 /work
EXPOSE 8080
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
