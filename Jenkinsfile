pipeline {
    agent any
    parameters {
        string(name: 'ENV', defaultValue: 'dev', description: 'Environment (dev/qa)')
        string(name: 'RUN_TYPE', defaultValue: 'FULL', description: 'Test type (UI, API, FULL)')
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser (chrome/headless)')
    }
    stages {
        // If you prefer to inject credentials securely from Jenkins, configure a credential (username/password)
        // in the Jenkins job and use `withCredentials` (example):
        // 
        // stage('Inject Secrets (optional)') {
        //   steps {
        //     withCredentials([usernamePassword(credentialsId: 'PTN_CREDENTIALS', usernameVariable: 'PTN_USER', passwordVariable: 'PTN_PASS')]) {
        //       sh "echo using credentials: $PTN_USER" // not recommended to echo passwords
        //       // Gradle will see env vars PTN_USER / PTN_PASS — ConfigManager can read these
        //     }
        //   }
        // }
        stage('Checkout') { steps { checkout scm } }
        stage('Test') {
            steps {
                script {
                    if (params.RUN_TYPE == 'API') {
                        sh "./gradlew clean test -Denv=${params.ENV} -Dgroups=api"
                    } else if (params.RUN_TYPE == 'UI') {
                        sh "./gradlew clean test -Denv=${params.ENV} -Dbrowser=${params.BROWSER} -Dgroups=ui"
                    } else {
                        sh "./gradlew clean test -Denv=${params.ENV} -Dbrowser=${params.BROWSER}"
                    }
                }
            }
        }
        stage('Allure') { steps { sh './gradlew allureReport' } }
    }
    post {
        always {
            junit 'build/test-results/**/*.xml'
            archiveArtifacts artifacts: 'build/allure-results/**', allowEmptyArchive: true
        }
    }
}
