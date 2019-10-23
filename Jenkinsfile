pipeline {
    agent any
    stages {
        stage('Build' ) {
            steps {
                sh "./mvnw clean verify"
                sh "./mvwn release:clean"
                sh "./mvnw release:prepare -B"
                sh "./mvnw release:perform"
            }
        }
        stage('Docker Publish') {
            steps {
                sh "docker build -t atomicpancakes/planet-pancakes:${BUILD_NUMBER} ."
                sh "docker push atomicpancakes/planet-pancakes:${BUILD_NUMBER}"
            }
        }
    }
 }