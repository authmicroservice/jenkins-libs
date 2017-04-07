package com.elevenware.jenkins.pipelines.platforms

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.helpers.ChefSteps

def build(PipelineContext context) {
    echo "building ${context.appName}"
}

def test(PipelineContext context) {
    echo "test ${context.appName}"
}

def publish(PipelineContext context) {
    echo "publish ${context.appName}"
}

def deploy(PipelineContext context, String env) {
    echo "deploy ${context.appName}"
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

def getVersion() {
    "1.0.2"
}
