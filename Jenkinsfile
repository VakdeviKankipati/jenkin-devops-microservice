pipeline {
    agent any

    tools {
        maven 'myMaven'    // Name from Global Tool Configuration
    }

    environment {
        // Registry credentials ID (set in Jenkins Credentials)
        DOCKERHUB_CREDENTIALS = 'dockerhub'
        IMAGE_NAME = "vakdevi/question-service"
        DOCKER_HOST = "unix:///var/run/docker.sock"
        DOCKER_TLS_VERIFY = ""
        DOCKER_CERT_PATH = ""
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
                    // Use shell command instead of Docker plugin to avoid TLS issues
                    sh """
                        export DOCKER_TLS_VERIFY=
                        export DOCKER_CERT_PATH=
                        export DOCKER_HOST=unix:///var/run/docker.sock
                        docker build -t ${IMAGE_NAME}:${env.BUILD_NUMBER} .
                        docker tag ${IMAGE_NAME}:${env.BUILD_NUMBER} ${IMAGE_NAME}:latest
                    """
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Use withCredentials for DockerHub login
                    withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS, 
                                                    usernameVariable: 'DOCKER_USERNAME', 
                                                    passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh """
                            export DOCKER_TLS_VERIFY=
                            export DOCKER_CERT_PATH=
                            export DOCKER_HOST=unix:///var/run/docker.sock
                            echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin
                            docker push ${IMAGE_NAME}:${env.BUILD_NUMBER}
                            docker push ${IMAGE_NAME}:latest
                            docker logout
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished (always runs)'
            // Clean up local images to save space
            script {
                sh """
                    export DOCKER_TLS_VERIFY=
                    export DOCKER_CERT_PATH=
                    export DOCKER_HOST=unix:///var/run/docker.sock
                    docker rmi ${IMAGE_NAME}:${env.BUILD_NUMBER} || true
                    docker rmi ${IMAGE_NAME}:latest || true
                """
            }
        }
        success {
            echo 'Build & Push Successful!'
        }
        failure {
            echo 'Build Failed!'
        }
    }
}

