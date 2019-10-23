pipeline {
    agent any
    stages {
        stage('Build' ) {
            steps {
                ./mvnw clean verify
                ./mvwn release:clean
                ./mvnw release:prepare -B
                ./mvnw release:perform
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