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
        sh '''kill `pidof hotpoll` &>/dev/null
JENKINS_NODE_COOKIE=dontKillMe nohup java -jar target/hotpoll-0.0.1-SNAPSHOT.jar  &'''
      }
    }

  }
}