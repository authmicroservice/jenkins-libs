package com.elevenware.jenkins.pipelines.elements

class BuildTestPublishElement extends PipelineElement {

    @Override
    void generate(steps) {
        steps.node {
            steps.stage("Build-Test-Publish") {
                steps.echo "Hello, world ${steps.getClass()}"
            }
        }
    }

}
