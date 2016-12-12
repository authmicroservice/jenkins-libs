package com.elevenware.jenkins.pipelines.definitions

def buildArtifact(Map config) {
    def platform = config.platform
    if(!platform) {
        println "No platform specified - assuming generic"
        platform = 'generic'
    }
    switch(platform) {
        default:
            buildGenericArtifact(config)
    }
}

def buildGenericArtifact(Map config) {
    println "Running generic build for ${config.appName}"
    new GenericBuildSteps().build()

}