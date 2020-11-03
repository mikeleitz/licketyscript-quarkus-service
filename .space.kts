job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        sequential {

            shellScript {
                content = "./gradlew build quarkusBuild --uber-jar publish"
            }

            shellScript {
                content = "ls -la build/libs/licketyscript-quarkus-service-1.0-SNAPSHOT.jar"
            }
            shellScript {
                content = "mkdir -p $mountDir/share"
            }
            shellScript {
                content = "cp build/libs/licketyscript-quarkus-service-1.0-SNAPSHOT.jar $mountDir/share/licketyscript-quarkus-service-1.0-SNAPSHOT.jar"
            }
        }
    }
}

/*
container("ubuntu") {
      shellScript {
          content = "ls -la $mountDir/share/licketyscript-quarkus-service-1.0-SNAPSHOT.jar"
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
  }*/
}
