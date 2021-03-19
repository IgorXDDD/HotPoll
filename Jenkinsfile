pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './mvnw clean install'
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
        sh '''docker build -t hotpoll .
docker run --rm --publish 0.0.0.0:4444:0.0.0.0:4444 -d hotpoll'''
      }
    }

  }
}