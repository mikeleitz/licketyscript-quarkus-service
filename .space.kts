job("build and publish") {
    docker {
        build {
            context = ""
            file = "Dockerfile"
        }
        push("leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service") {
            tag = "latest"
        }
    }
}
