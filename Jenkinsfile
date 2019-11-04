pipeline {
    agent any
    stages {
        stage('Maven Build' ) {
            steps {
                echo 'Building Project'
                sh "./mvnw clean verify"
            }
        }
        stage('Docker Build') {
            steps {
                echo 'Building Docker Image'
                script {
                    def image = docker.build("atomicpancakes/planet-pancakes")
                }
            }
        }
        stage('Docker Publish') {
            steps {
                echo 'Publishing Docker Image'

                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
                        image.push("${BRANCH_NAME}-${BUILD_NUMBER}")
                        image.push("latest")
                    }
                }
            }
        }
    }
}