package com.elevenware.jenkins.pipelines.functions

def deploy(String env, Map config) {
    def role = config.role
    node {
        String rootDeployScript = ''// libraryResource 'scripts/tracer.sh'
        stage("deploy-${role}-${env}") {
            echo "Deploying ${role} to ${env}"
            def deployScript = rootDeployScript.replace('${ENVIRONMENT}', env)
            sh "${deployScript}"
        }
    }
}