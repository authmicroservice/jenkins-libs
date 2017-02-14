package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.util.PlatformRegistry

def run(PipelineContext context) {
      String appName = context.appName
      def platform = context.platformImplementation
      echo "USING PLATFORM $platform"
      node {
          stage("Build $appName") {
             platform.build(context)
          }
      }
     node {
        deploy(context, "integration")
     }
    node {
        deploy(context, "QA")
    }
    node {
        deploy(context, "staging")
    }
    node {
        deploy(context, "production")
    }
}


def deploy(PipelineContext context, String env) {
    stage("Deploy ${context.appName} to $env") {
        echo "Deploying ${context.appName} to $env"
        def chefUri = context.chefRepoUri
        def cookbookDir = context.cookbookDir
        git chefUri


    }
}