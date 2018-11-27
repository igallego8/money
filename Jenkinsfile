pipeline {
  agent any
  stages {
    stage('Pull from Repo') {
      steps {
        git(url: 'https://github.com/igallego8/money.git', credentialsId: 'igallego8', poll: true)
      }
    }
    stage('Compile') {
      steps {
        tool 'maven3'
      }
    }
  }
}