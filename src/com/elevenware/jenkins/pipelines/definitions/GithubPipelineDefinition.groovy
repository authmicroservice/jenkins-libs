package com.elevenware.jenkins.pipelines.definitions

def buildAndPublishArtifact(Map config) {
    def platform = config.platform
    if(!platform) {
        println "No platform specified - assuming generic"
        platform = 'generic'
    }
    switch(platform) {
        case 'java':
            buildAndPublishMavenArtifact(config)
            break
        default:
            buildAndPublishGenericArtifact(config)
    }
}

def buildAndPublishMavenArtifact(Map config) {
    println "Running maven for ${config.appName}"
    withMaven(maven: 'M3') {
        sh "mvn clean deploy --quiet"
    }
}

def buildAndPublishGenericArtifact(Map config) {
    println "Running generic build for ${config.appName}"
    new GenericBuildSteps().build()

}

def deploy(Map config, String environment) {
    stage("deploy-${config.appName}-${environment}") {
        echo "Deploying ${config.role} to ${environment}"
        new ChefSteps().pinEnvironment(config)
    }
}