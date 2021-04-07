pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './mvnw clean install'
        script {
          publishHTML( target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: './target/site/jacoco', reportFiles: 'index.html', reportName: 'Jacoco report', reportTitles: 'Jacoco report'])
        }

      }
    }

    stage('Test') {
      steps {
        echo 'test'
      }
    }

    stage('Deploy') {
      parallel {
        stage('Deploy') {
           environment {
               pom = readMavenPom file: "pom.xml";
               filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
               ARTIFACT_PATH = "${filesByGlob[0].path}"
           }
          steps {
            sh 'chmod +x ./scripts/deliver.sh'
            sh './scripts/deliver.sh ${ARTIFACT_PATH}'
          }
        }

        stage('Nexus') {
          steps {
            script {
              NEXUS_VERSION = "nexus3"
              NEXUS_PROTOCOL = "http"
              NEXUS_URL = "192.168.162.225:8081"
              NEXUS_REPOSITORY = "maven-nexus-repo"
              NEXUS_CREDENTIAL_ID = "pawelmasluch"
              pom = readMavenPom file: "pom.xml";
              filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
              echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
              artifactPath = filesByGlob[0].path;
              artifactExists = fileExists artifactPath;
              if(artifactExists) {
                echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                                nexusArtifactUploader(
                                    nexusVersion: NEXUS_VERSION,
                                    protocol: NEXUS_PROTOCOL,
                                    nexusUrl: NEXUS_URL,
                                    groupId: pom.groupId,
                                    version: pom.version,
                                    repository: NEXUS_REPOSITORY,
                                    credentialsId: NEXUS_CREDENTIAL_ID,
                                    artifacts: [
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: artifactPath,
                                        type: pom.packaging],
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: "pom.xml",
                                        type: "pom"]
                                      ]
                                    );
                  } else {
                    error "*** File: ${artifactPath}, could not be found";
                  }
                }

              }
            }

          }
        }

      }
    }