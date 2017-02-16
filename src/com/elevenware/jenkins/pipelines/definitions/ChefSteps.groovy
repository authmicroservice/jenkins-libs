package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext

def installChefDependencies(PipelineContext ctx) {
    echo'Installing cookbook dependencies'
    echo "I AM IN ${env.WORKSPACE} ${ctx.cookbookDir}"
    dir ctx.cookbookDir
    sh "bundle install --path \"~/.gem\""
}

def environmentPin(PipelineContext ctx, String env) {
    echo "Pinning ${ctx.appName} to version <x> in environment ${env}"
    echo "Build number: ${ctx.buildNumber}"
    def metadata = String.format("%04d-%s", ctx.buildNumber, ctx.shortCommit)
    echo "Metadata: ${metadata}"
}