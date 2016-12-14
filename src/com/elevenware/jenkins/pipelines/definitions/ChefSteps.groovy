package com.elevenware.jenkins.pipelines.definitions

def pinEnvironment() {
    def script = libraryResource('scripts/chef/environmentPin.sh')
    sh script "(using magic)"
}
