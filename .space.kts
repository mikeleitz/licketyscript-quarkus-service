job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        shellScript {
            content = """
                    ./gradlew build quarkusBuild --uber-jar publish
                    pwd
                    ls -lahrt
                    mkdir -p /mnt/space/work/build/libs
                    cp build/libs/licketyscript-quarkus-service-1.0-SNAPSHOT.jar /mnt/space/work/build/libs
                """
        }
    }
    docker {
        beforeBuildScript {
            content = """
                pwd
                ls -lahrt
                """
        }
        build {
            context = """
                    pwd
                    ls -lahrt
                """
            file = "/mnt/space/work/src/main/docker/Dockerfile.jvm"
        }
        push("leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service") {
            tag = "latest"
        }
    }
}
