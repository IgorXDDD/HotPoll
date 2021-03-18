pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh '/bin/sh -c \'./mvnw spring-boot:run\''
      }
    }

  }
}