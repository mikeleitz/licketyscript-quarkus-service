job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        shellScript {
            content = "./gradlew build quarkusBuild --uber-jar publish && ls -la build/libs/licketyscript-quarkus-service-1.0-SNAPSHOT.jar && mkdir -p $mountDir/share && cp * $mountDir/share/"
        }
    }
    docker {
        build {
            context = "/mnt/space/work"
            file = "src/main/docker/Dockerfile.jvm"
        }
        push("leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service") {
            tag = "latest"
        }
    }
}
