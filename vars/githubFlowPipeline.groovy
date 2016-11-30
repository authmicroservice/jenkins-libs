import com.elevenware.jenkins.pipelines.functions.Deployments

def call(Closure body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def d = new com.elevenware.jenkins.pipelines.functions.Deployments()

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
        d.deploy('integration')
        d.deploy('qa')
        d.deploy('staging')
        d.deploy('production')

    }
}

