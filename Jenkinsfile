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
BUILD_ID=dontKillMe nohup java -jar target/*.${pom.packaging}  &'''
      }
    }

  }
}