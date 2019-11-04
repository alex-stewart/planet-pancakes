pipeline {
    agent any
    stages {
        stage('Maven Build' ) {
            steps {
                sh "./mvnw clean verify"
            }
        }
        stage('Docker Build') {
            def image = docker.build("atomicpancakes/planet-pancakes")
        }
        stage('Docker Publish') {
            docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
                image.push("${BRANCH_NAME}-${BUILD_NUMBER}")
                image.push("latest")
            }
        }
    }
}