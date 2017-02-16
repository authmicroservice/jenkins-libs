package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext

def installChefDependencies(PipelineContext ctx) {
    echo'Installing cookbook dependencies'
    dir ctx.cookbookDir
    sh "bundle install --path \"~/.gem\""
}

def environmentPin(PipelineContext ctx, String env) {
    echo "Pinning ${ctx.appName} to version <x> in environment ${env}"
    echo "Build number: ${ctx.buildNumber}"
    def metadata = String.format("%04d", ctx.buildNumber)
    echo "Metadata: ${metadata}"
}