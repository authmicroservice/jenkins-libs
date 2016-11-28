package com.elevenware.jenkins.pipelines.elements

import com.cloudbees.groovy.cps.NonCPS
import com.elevenware.jenkins.pipelines.Platform

abstract class PipelineElement implements Serializable {

    private PipelineElement next

    PipelineElement(PipelineElement next) {
        this.next = next
    }

    void doGenerate(Platform platform) {
        generate(platform)
        next.doGenerate(platform)
    }

    @NonCPS
    abstract void generate(Platform platform)

}
