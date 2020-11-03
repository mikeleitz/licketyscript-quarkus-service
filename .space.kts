job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        kotlinScript { api ->
            api.gradlew("quarkusBuild", "--uber-jar")
            try {
                api.gradlew("publish")
            } catch (ex: Exception) {
                println("Publishing failed")
            }
        }

        shellScript {
            content = "ls -l /mnt/space/"
        }
    }

    container("ubuntu") {
        shellScript {
            content = "ls -la $mountDir/share/licketyscript-quarkus-service-1.0-SNAPSHOT.jar"
        }
    }
/*
    docker {
        build {
            context = "/mnt/space/work"
            file = "src/main/docker/Dockerfile.jvm"
        }
        push("leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service") {
            tag = "latest"
        }
    }*/
}
