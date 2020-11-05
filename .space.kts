job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        shellScript {
            content = """
                    ./gradlew build quarkusBuild --uber-jar publish
                    mkdir -p $mountDir/share/
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
                ls -lahrt /mnt/
                ls -lahrt /mnt/space/
                ls -lahrt /mnt/space/share
                ls -lahrt /mnt/space/work
                mkdir -p build/libs
                mv /mnt/space/share/licketyscript-quarkus-service-1.0-SNAPSHOT.jar build/libs
                mkdir -p /run/user/$UID
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
