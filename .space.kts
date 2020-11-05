job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        shellScript {
            content = """
                    ./gradlew build quarkusBuild --uber-jar publish
                    mkdir -p /mnt/space/work/build/libs
                    ls -lahrt build/libs/licketyscript-quarkus-service-1.0-SNAPSHOT.jar
                    cp build/libs/licketyscript-quarkus-service-1.0-SNAPSHOT.jar $mountDir/share/licketyscript-quarkus-service-1.0-SNAPSHOT.jar
                """
        }
    }
    docker {
        beforeBuildScript {
            content = """
                pwd
                ls -lahrt src/main/docker/Dockerfile.jvm
                ls -lahrt $mountDir/share
                """
        }
        build {
            context = ""
            file = "src/main/docker/Dockerfile.jvm"
        }
        push("leadtechnologist.registry.jetbrains.space/p/lsc/leadtechnologist-containers/licketyscript-quarkus-service") {
            tag = "latest"
        }
    }
}
