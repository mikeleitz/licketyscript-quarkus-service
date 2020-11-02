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
