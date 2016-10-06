def call(Closure body) {
    node {
        def mvnHome
        stage('Checkout') { // for display purposes
            // Get some code from a GitHub repository
            checkout scm
//            git url: scm.url, credentialsId: '9af674bb-7bea-4eee-9332-17bd93dffb14'
        }
        stage('Build-test-publish') {
            withMaven(maven: 'M3') {
                // Run the maven build
                sh "mvn clean install"
            }
        }
        stage('deploy-integration') {
            echo "Deploying to integration"
        }
        stage('deploy-qa') {
            echo "Deploying to QA"
        }
        stage('deploy-staging') {
            echo "Deploying to staging"
        }
        stage('deploy-production') {
            echo "Deploying to production"
        }
    }
}