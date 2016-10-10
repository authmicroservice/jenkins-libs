package com.elevenware.jenkins.pipelines.elements

import com.elevenware.jenkins.pipelines.Platform

abstract class PipelineElement implements Serializable {

    @NonCPS
    abstract void generate(Platform platform)

}
