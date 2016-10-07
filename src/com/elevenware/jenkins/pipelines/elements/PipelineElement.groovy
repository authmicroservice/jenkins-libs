package com.elevenware.jenkins.pipelines.elements

abstract class PipelineElement {

    @NonCPS
    abstract void generate(steps)

}
