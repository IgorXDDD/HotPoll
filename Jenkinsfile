pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './mvnw spring-boot:run'
      }
    }

    stage('Test') {
      steps {
        echo 'test'
      }
    }

    stage('Deploy') {
      steps {
        echo 'deploy'
      }
    }

  }
}