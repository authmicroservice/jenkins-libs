package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext

def installChefDependencies(PipelineContext ctx) {
    echo'Installing cookbook dependencies'
    sh 'ls'
    dir("${ctx.cookbookDir}") {
        sh ShellSnippets.GEM_INSTALL.code
    }
}

def environmentPin(PipelineContext ctx, String targetEnvironment) {
    echo "Pinning ${ctx.appName} to version ${ctx.appSpec} in environment ${targetEnvironment}"
    echo "Build number: ${ctx.buildNumber}"
    def metadata = String.format("%04d-%s", ctx.buildNumber, ctx.shortCommit)
    echo "Metadata: ${metadata}"

    def ret = sh ShellSnippets.KNIFE_CHECK_ENV.format(targetEnvironment)
    echo "RET : $ret"
    if(ret != 0) {
        echo "Could not find environment ${targetEnvironment}"
        return -1
    }

    echo "now let's pin"
}