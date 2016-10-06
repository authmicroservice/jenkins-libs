def call(Closure body) {
    node {
        String rootDeployScript = loadResource 'scripts/tracer.sh'
        stage('Checkout') { // for display purposes
            // Get some code from a GitHub repository
            checkout scm
//            git url: scm.url, credentialsId: '9af674bb-7bea-4eee-9332-17bd93dffb14'
        }
        stage('Build-test-publish') {
            withMaven(maven: 'M3') {
                // Run the maven build
                sh "env"

                sh 'ls'
                sh "echo 'run maven'" //mvn clean install"
            }
        }
        stage('deploy-integration') {
            echo "Deploying to integration"
            def deployScript = rootDeployScript.replace("${ENVIRONMENT}", "integration")
            sh "${deployScript}"
        }
        stage('deploy-qa') {
            echo "Deploying to QA"
            def deployScript = rootDeployScript.replace("${ENVIRONMENT}", "QA")
            sh "${deployScript}"
        }
        stage('deploy-staging') {
            echo "Deploying to staging"
            def deployScript = rootDeployScript.replace("${ENVIRONMENT}", "staging")
            sh "${deployScript}"
        }
        stage('deploy-production') {
            echo "Deploying to production"
            def deployScript = rootDeployScript.replace("${ENVIRONMENT}", "production")
            sh "${deployScript}"
        }
    }
}