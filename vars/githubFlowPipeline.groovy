def call(Closure body) {
    node {
        String rootDeployScript = libraryResource 'scripts/tracer.sh'
        stage('Checkout') {
            checkout scm
        }
        stage('Build-test-publish') {
            withMaven(maven: 'M3') {
                sh "echo 'run maven'" //mvn clean install"
            }
        }
        stage('deploy-integration') {
            echo "Deploying to integration"
            def deployScript = rootDeployScript.replace('${ENVIRONMENT}', "integration")
            sh "${deployScript}"
        }
        stage('deploy-qa') {
            echo "Deploying to QA"
            def deployScript = rootDeployScript.replace('${ENVIRONMENT}', "QA")
            sh "${deployScript}"
        }
        stage('deploy-staging') {
            echo "Deploying to staging"
            def deployScript = rootDeployScript.replace('${ENVIRONMENT}', "staging")
            sh "${deployScript}"
        }
        stage('deploy-production') {
            echo "Deploying to production"
            def deployScript = rootDeployScript.replace('${ENVIRONMENT}', "production")
            sh "${deployScript}"
        }
    }
}