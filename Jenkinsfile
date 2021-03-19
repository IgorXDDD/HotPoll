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
docker run --rm --publish 127.0.0.1:4444:4444 -d hotpoll'''
      }
    }

  }
}