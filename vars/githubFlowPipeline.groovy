def call(Closure body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

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
        stage("deploy-integration-${config.role}") {
            echo "Deploying to ${config.role} integration"
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