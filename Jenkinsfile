pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './mvn clean install'
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
docker run -p 4444:4444 -d hotpoll'''
      }
    }

  }
}