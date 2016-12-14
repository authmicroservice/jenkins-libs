package com.elevenware.jenkins.pipelines.definitions

class ChefSteps {

    def pinEnvironment() {
        def script = libraryResource('scripts/chef/environmentPin.sh')
        sh script
    }

}
