package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext
import groovy.json.JsonSlurper

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

    sh ShellSnippets.KNIFE_CHECK_ENV.format(targetEnvironment)

    StringBuilder pinCmdBuilder = new StringBuilder().append("bundle exec knife exec -E ")
            .append("\"env = environments.find('name:${targetEnvironment}').first;")
            .append("env.default_attributes['apps'] ||= {};")
            .append("env.default_attributes['apps']['${ctx.appName}'] = '${ctx.appSpec}';")
            .append("env.save;\"")

    currentVersion(ctx, targetEnvironment)
    sh pinCmdBuilder.toString()

}

def currentVersion(PipelineContext ctx, String targetEnvironment) {

    StringBuilder buf = new StringBuilder().append("bundle exec knife environment show ${targetEnvironment} ")
                .append("--attribute \"cookbook_versions.${ctx.cookbookName}\" --format json")

    def ret = sh(returnStdout: true, script: buf.toString())
    JsonSlurper slurper = new JsonSlurper()
    def data = slurper.parseText(ret)
    def currentVersion = data[targetEnvironment]["cookbook_versions.${ctx.cookbookName}"]
    echo "current version is '${currentVersion}'"
    return currentVersion

}