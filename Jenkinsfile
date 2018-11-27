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
        sh '''def maven_tool= tool name: \'maven\', type: \'maven\'
      def maven_home = "${maven_tool}/bin/mvn"
      sh "${maven_home} clean package"'''
      }
    }
  }
}