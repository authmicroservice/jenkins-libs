package com.elevenware.jenkins.pipelines.definitions

def pinEnvironment() {
    String script = libraryResource('scripts/chef/environmentPin.sh')
    script.replaceAll('${ROLE}', 'fof')
    sh script
}
