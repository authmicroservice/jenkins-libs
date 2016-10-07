package com.elevenware.jenkins.pipelines.elements

class BuildTestPublishElement extends PipelineElement {
    @Override
    void generate(steps) {
        steps.echo "OHAI"
    }
}
