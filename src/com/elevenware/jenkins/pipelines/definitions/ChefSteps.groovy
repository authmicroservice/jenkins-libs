package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext
import groovy.json.JsonSlurper

def installChefDependencies(PipelineContext ctx) {
    echo'Installing cookbook dependencies'
    sh 'ls'
    dir("${ctx.cookbookDir}") {
        sh CommonShellCommands.GEM_INSTALL.code
    }
}

def environmentPin(PipelineContext ctx, String targetEnvironment) {
    echo "Pinning ${ctx.appName} to version ${ctx.appSpec} in environment ${targetEnvironment}"
    def metadata = String.format("%04d-%s", ctx.buildNumber, ctx.shortCommit)

    sh KnifeCommands.checkEnvExists(targetEnvironment)

    ctx.priorAppSpec = grabCurrentVersion(ctx, targetEnvironment)
    sh KnifeCommands.pinEnvironment(ctx, targetEnvironment)
    echo "PIUNNED"

}

def runChefClient(PipelineContext ctx, String targetEnvironment) {

    echo "Running Chef client on all nodes with role ${ctx.role} in environment ${targetEnvironment}"

    sh "bundle exec knife search \"role:${ctx.role} AND chef_environment:${targetEnvironment}\" \\\n" +
            "             -a name -a ipaddress | \\\n" +
            "             grep -e name -e ipaddress"

    sh "bundle exec knife ssh \"role:${ctx.role} AND chef_environment:${targetEnvironment}\" \\\n" +
            "                        --attribute ipaddress \\\n" +
            "                        --no-host-key-verify \\\n" +
            "                        --ssh-user jenkins \\\n" +
            "                        -i /home/jenkins/.ssh/cloud-user \\\n" +
            "                       'sudo chef-client'"

}

def grabCurrentVersion(PipelineContext ctx, String targetEnvironment) {

    StringBuilder buf = new StringBuilder().append("bundle exec knife environment show ${targetEnvironment} ")
                .append("--attribute \"cookbook_versions.${ctx.cookbookName}\" --format json")

    def ret = sh(returnStdout: true, script: buf.toString())
    JsonSlurper slurper = new JsonSlurper()
    def data = slurper.parseText(ret)
    def currentVersion = data[targetEnvironment]["cookbook_versions.${ctx.cookbookName}"]
    echo "current version is '${currentVersion}'"

    return currentVersion

}