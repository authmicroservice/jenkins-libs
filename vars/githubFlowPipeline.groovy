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
        deploy('integration')
     
    }
}

def deploy(String env) {
    node {
        stage("deploy-${env}") {
            echo "Deploying to ${env}"
            def deployScript = rootDeployScript.replace('${ENVIRONMENT}', env)
            sh "${deployScript}"
        }
    }
}