package com.elevenware.jenkins.pipelines.elements

class BuildTestPublishElement extends PipelineElement {

    @Override
    Closure getDefinition() {
       return {
            node {
                stage("Build-Test-Publish") {
                   echo("Hello, world!")
                }
            }
        }
    }
}
