 package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.util.PlatformRegistry

def run(PipelineContext context) {
      String appName = context.appName
      def platform = context.platformImplementation
      node {
          stage("Build $appName") {
             platform.build(context)
          }
      }
        deploy(context, "integration")
        deploy(context, "qa")
        deploy(context, "staging")
        deploy(context, "production")
}


def deploy(PipelineContext context, String env) {
    def appName = context.appName
    stage("Promote ${appName} to ${env}") {
        input(id:'promote', message: "Promote to ${env}?")
    }
    node {
        stage("Deploy ${appName} to $env") {
            echo "Deploying ${appName} to $env"
            def chefUri = context.chefRepoUri
            def credentials = context.chefRepoCredentials
            def cookbookDir = context.cookbookDir
            git url: chefUri, credentialsId: credentials
            ChefSteps chefSteps = context.chefSteps
            chefSteps.installChefDependencies(context)
            chefSteps.environmentPin(context, env)
            chefSteps.runChefClient(context, env)
        }
    }
}