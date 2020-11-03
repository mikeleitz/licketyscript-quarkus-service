job("build and publish") {
    container("oracle/graalvm-ce:20.2.0-java11") {
        shellScript {
            content = "./gradlew build quarkusBuild --uber-jar publish && ls -la build/libs/licketyscript-quarkus-service-1.0-SNAPSHOT.jar && mkdir -p $mountDir/share && cp build/libs/licketyscript-quarkus-service-1.0-SNAPSHOT.jar $mountDir/share/licketyscript-quarkus-service-1.0-SNAPSHOT.jar"
        }
    }
}
