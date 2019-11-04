pipeline {
    agent any
    stages {
        stage('Maven Build' ) {
            steps {
                sh "./mvnw clean verify"
            }
        }
        stage('Docker Publish') {
            docker.withRegistry("https://registry.hub.docker.com", "docker-hub") {
                def image = docker.build("atomicpancakes/planet-pancakes")
                image.push("${BRANCH_NAME}-${BUILD_NUMBER}")
                image.push("latest")
            }
        }
    }
}