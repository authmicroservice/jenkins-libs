package com.elevenware.jenkins.pipelines.definitions

def pinEnvironment() {
    String script = libraryResource('scripts/chef/environmentPin.sh')
    script.replace('${ROLE}', 'foo')
    echo "Script is ${script}"
    sh script
}
