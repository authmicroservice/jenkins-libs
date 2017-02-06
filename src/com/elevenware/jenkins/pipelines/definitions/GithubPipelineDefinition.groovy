package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.util.PlatformRegistry

def run(PipelineContext context) {
      String appName = context.appName
      def platform = PlatformRegistry.instance.create(context.platform)
      node {
          stage("Build $appName") {
             platform.build(context)
          }
      }
}

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
    config['environment'] = environment
    stage("deploy-${config.appName}-${environment}") {
        echo "Deploying ${config.role} to ${environment}"
        new ChefSteps().pinEnvironment(config)
    }
}