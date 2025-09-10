pipeline {
    agent any

    tools {
        maven 'myMaven'    // Name from Global Tool Configuration
    }

    environment {
        // Registry credentials ID (set in Jenkins Credentials)
        DOCKERHUB_CREDENTIALS = 'dockerhub'
        IMAGE_NAME = "vakdevi/question-service"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                sh 'mvn --version'
                sh 'docker --version'
            }
        }

        stage('Compile') {
            steps {
                sh "mvn clean compile"
            }
        }

        stage('Test') {
            steps {
                sh "mvn test"
            }
        }

        stage('Integration Test') {
            steps {
                sh "mvn failsafe:integration-test failsafe:verify"
            }
        }

        stage('Package') {
            steps {
                sh "mvn package -DskipTests"
            }
        }

        stage('Build Docker Image') {
    steps {
        script {
            withEnv(["DOCKER_TLS_VERIFY=", "DOCKER_CERT_PATH="]) {
                dockerImage = docker.build("${IMAGE_NAME}:${env.BUILD_NUMBER}")
            }
        }
    }
}

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
                        dockerImage.push()
                        dockerImage.push('latest')
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished (always runs)'
        }
        success {
            echo 'Build & Push Successful!'
        }
        failure {
            echo 'Build Failed!'
        }
    }
}
