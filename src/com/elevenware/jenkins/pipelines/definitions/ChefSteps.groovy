package com.elevenware.jenkins.pipelines.definitions

import hudson.FilePath
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

def pinEnvironment(Map config) {
    loadChefBundle()
    echo "CONFIG = ${config}"
    String script = libraryResource('scripts/chef/environmentPin.sh')
    script = script.replace('${COOKBOOK_VERSION}', config.version)
    script = script.replace('${CHEF_ENVIRONMENT', config.environment)
    sh script
}

def loadChefBundle() {
    String gemfileContent = libraryResource 'scripts/chef/chef_gemfile'
    writeFile file: 'Gemfile', text: gemfileContent
    sh(libraryResource('scripts/chef/loadDependencies.sh'))
}