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
        sh '''ls
docker build -t hotpoll .
docker run --rm --publish 4444:4444 --network host -d hotpoll'''
      }
    }

  }
}