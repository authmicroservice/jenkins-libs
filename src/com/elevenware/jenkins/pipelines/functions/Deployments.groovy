package com.elevenware.jenkins.pipelines.functions

def deploy(String env) {
    node {
        String rootDeployScript = libraryResource 'scripts/tracer.sh'
        stage("deploy-${env}") {
            echo "Deploying to ${env}"
            def deployScript = rootDeployScript.replace('${ENVIRONMENT}', env)
            sh "${deployScript}"
        }
    }
}