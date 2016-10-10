package com.elevenware.jenkins.pipelines.elements

abstract class PipelineElement implements Serializable {

    @NonCPS
    abstract void generate()

}
