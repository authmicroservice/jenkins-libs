package com.elevenware.jenkins.pipelines.definitions

import hudson.FilePath
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

def pinEnvironment(Map config) {
    loadChefBundle()
    echo "VERSION = ${config.version}"
    String script = libraryResource('scripts/chef/environmentPin.sh')
    script = script.replace('${ROLE}', 'foo')
    sh script
}

def loadChefBundle() {
    String gemfileContent = libraryResource 'scripts/chef/chef_gemfile'
    writeFile file: 'Gemfile', text: gemfileContent
    sh(libraryResource('scripts/chef/loadDependencies.sh'))
}