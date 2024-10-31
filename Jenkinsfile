pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    stages {
        stage('GIT') {
            steps {
                script {
                    // Check out the specified branch from SCM
                    git branch: 'testJunit',
                        url: 'https://github.com/nouhasedraoui/DevOps--JunitTest.git'
                }
            }
        }

        stage('Compile Stage') {
            steps {
                script {
                    // Check out the latest code from the branch again before compiling
                    checkout scm
                }
                sh 'mvn clean compile'
            }
        }

        stage('Scan') {
            steps {
                script {
                    // Check out the latest code from the branch again before scanning
                    checkout scm
                }
                withSonarQubeEnv('sq1') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}
