job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        shellScript {
            content = """
                    ./gradlew build -Dquarkus.package.type=native
                    pwd
                    ls -lahrt
                    mkdir -p /mnt/space/work/build/libs
                    ls -lahrt /mnt/space/work/build/*-runner 
                """
        }
    }
    docker {
        build {
            context = "/mnt/space/work"
            file = "/mnt/space/work/src/main/docker/Dockerfile.native"
        }
        push("leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service") {
            tag = "latest"
        }
    }
}
