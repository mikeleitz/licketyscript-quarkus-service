job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        shellScript {
            content = """
                    ./gradlew build quarkusBuild --uber-jar publish
                    pwd
                    ls -lahrt
                    cp ./ mnt/space/share
                """
        }
    }
    docker {
        beforeBuildScript {
            content = "cp mnt/space/share docker"
        }
        build {
            context = "docker"
            file = "src/main/docker/Dockerfile.jvm"
        }
        push("leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service") {
            tag = "latest"
        }
    }
}
