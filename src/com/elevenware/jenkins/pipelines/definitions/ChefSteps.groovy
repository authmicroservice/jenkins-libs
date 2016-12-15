package com.elevenware.jenkins.pipelines.definitions

def pinEnvironment(Map config) {
    echo "VERSION = ${config.version}"
    String script = libraryResource('scripts/chef/environmentPin.sh')
    script = script.replace('${ROLE}', 'foo')
    sh script
}
