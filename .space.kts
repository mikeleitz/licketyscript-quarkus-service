job("build and publish") {
    container("docker") {
        shellScript {
            content = """
                    apk add openjdk11
                    ./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true -Dquarkus.native.builder-image="leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/ubi-quarkus-native-image:20.2.0-java11"
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
