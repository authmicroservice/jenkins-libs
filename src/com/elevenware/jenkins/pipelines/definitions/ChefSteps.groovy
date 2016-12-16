package com.elevenware.jenkins.pipelines.definitions

def pinEnvironment(Map config) {
    loadChefBundle()
    echo "VERSION = ${config.version}"
    String script = libraryResource('scripts/chef/environmentPin.sh')
    script = script.replace('${ROLE}', 'foo')
    sh script
}

def loadChefBundle() {
    String gemfile = libraryResource 'scripts/chef/chef_gemfile'
    sh "cat ${gemfile} > Gemfile"
    sh 'ls'
}