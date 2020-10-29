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
}
