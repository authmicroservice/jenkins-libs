import com.elevenware.jenkins.pipelines.functions.Deployments
import com.elevenware.jenkins.pipelines.definitions.GithubPipelineDefinition

def call(Closure body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def d = new com.elevenware.jenkins.pipelines.functions.Deployments()
    def githubFlow = new GithubPipelineDefinition()

    node {
        stage('Checkout') {
            checkout scm
        }
        stage("build-${config.appName}") {
            githubFlow.buildAndPublishArtifact(config)
        }
    }
    node {
        stage("Deploy-${config.appName}-to-int") {
            echo "deploying to int"
        }
    }
//    node {
//        stage('Build-test-publish') {
//            withMaven(maven: 'M3') {
//                sh "echo 'run maven'" //mvn clean install"
//            }
//        }
//
//
//    }
//    d.deploy('integration', config)
//    d.deploy('qa', config)
//    d.deploy('staging', config)
//    d.deploy('production', config)


}

