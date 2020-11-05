job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        shellScript {
            content = """
                    ./gradlew build quarkusBuild --uber-jar publish
                    pwd
                    ls -lahrt
                    mkdir -p /mnt/space/work/build/libs
                    ls -lahrt /mnt/space/work/src/main/docker/Dockerfile.jvm
                """
        }
    }
    docker {
        build {
            context = ""
            file = "/mnt/space/work/src/main/docker/Dockerfile.jvm"
        }
        push("leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service") {
            tag = "latest"
        }
    }
}
