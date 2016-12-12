package com.elevenware.jenkins.pipelines.definitions

def buildArtifact(Map config) {
    def platform = config.platform
    if(!platform) {
        println "No platform specified - assuming generic"
        platform = 'generic'
    }
    switch(platform) {
        case 'java':
            buildMavenArtifact(config)
            break
        default:
            buildGenericArtifact(config)
    }
}

def buildMavenArtifact(Map config) {
    println "Running maven for ${config.appName}"
    withMaven(maven: 'M3') {
        sh "mvn clean install"
    }
}

def buildGenericArtifact(Map config) {
    println "Running generic build for ${config.appName}"
    new GenericBuildSteps().build()

}