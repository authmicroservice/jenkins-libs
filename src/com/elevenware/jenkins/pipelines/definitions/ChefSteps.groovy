package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext

def installChefDependencies(PipelineContext ctx) {
    echo'Installing cookbook dependencies'
    sh 'ls'
    dir("${ctx.cookbookDir}") {
        sh "echo PATH: $PATH"
        sh ShellSnippets.GEM_INSTALL.code
    }
}

def environmentPin(PipelineContext ctx, String targetEnvironment) {
    echo "Pinning ${ctx.appName} to version <x> in environment ${targetEnvironment}"
    echo "Build number: ${ctx.buildNumber}"
    def metadata = String.format("%04d-%s", ctx.buildNumber, ctx.shortCommit)
    echo "Metadata: ${metadata}"
}