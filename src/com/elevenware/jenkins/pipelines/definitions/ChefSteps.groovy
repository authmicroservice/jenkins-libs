package com.elevenware.jenkins.pipelines.definitions

import hudson.FilePath

def pinEnvironment(Map config) {
    loadChefBundle()
    echo "VERSION = ${config.version}"
    String script = libraryResource('scripts/chef/environmentPin.sh')
    script = script.replace('${ROLE}', 'foo')
    sh script
}

def loadChefBundle() {
    String gemfileContent = libraryResource 'scripts/chef/chef_gemfile'
    def currentVars = currentBuild.buildVariables
    if(currentBuild.workspace.isRemote())
    {
        def channel = currentBuild.workspace.channel;
        def fp = new FilePath(channel, currentBuild.workspace.toString() + "/Gemfile")
    } else {
        fp = new FilePath(new File(currentBuild.workspace.toString() + "/Gemfile"))
    }

    if(fp != null)
    {
        fp.write(gemfileContent, null); //writing to file
    }
    sh 'ls'
}