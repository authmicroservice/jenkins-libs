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
            gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
// short SHA, possibly better for chat notifications, etc.
            shortCommit = gitCommit.take(8)
            config['version'] = "${currentBuild.number}-${shortCommit}"
        }
        stage("build-${config.appName}") {
            githubFlow.buildAndPublishArtifact(config)
        }
    }
    node {
        githubFlow.deploy(config, 'integration')
    }
    node {
        githubFlow.deploy(config, 'QA')
    }
    node {
        githubFlow.deploy(config, 'staging')
    }
    node {
        githubFlow.deploy(config, 'production')
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

