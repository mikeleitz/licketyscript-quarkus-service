job("build and publish") {
    container("gradle:6.7.0-jdk11") {
        kotlinScript { api ->
            api.gradle("build")
            try {
                api.gradle("publish")
            } catch (ex: Exception) {
                println("Publishing failed")
            }
        }
    }
}
