pipeline {
    agent any
    stages {
        stage('Maven Build' ) {
            steps {
                sh "./mvnw clean verify"
            }
        }
        stage('Docker Publish') {
            steps {
                sh "docker build -t atomicpancakes/planet-pancakes:${BRANCH_NAME}-${BUILD_NUMBER} ."
                sh "docker push atomicpancakes/planet-pancakes:${BRANCH_NAME}-${BUILD_NUMBER}"
            }
        }
    }
}