pipeline {
    agent any
    stages {
        stage('Maven Build' ) {
            steps {
                echo 'Building Project'
                sh "./mvnw clean verify"
            }
        }
        stage('Docker Build and Publish') {
            steps {
                echo 'Build and Publish Docker Image'

                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
                        def image = docker.build("atomicpancakes/planet-pancakes")
                        image.push("${BRANCH_NAME}-${BUILD_NUMBER}")
                        image.push("latest")
                    }
                }
            }
        }
    }
}