job("build and publish") {
    container("docker") {
        shellScript {
            content = """
                    apt-get update && \
                    apt-get install -y openjdk-11-jre-headless && \
                    apt-get clean;                
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
