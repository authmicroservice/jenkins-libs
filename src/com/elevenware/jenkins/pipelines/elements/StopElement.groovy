package com.elevenware.jenkins.pipelines.elements

import com.cloudbees.groovy.cps.NonCPS
import com.elevenware.jenkins.pipelines.Platform

class StopElement extends PipelineElement {

    StopElement() {
        super(null)
    }

    @Override
    void generate(Platform platform) {
        // nowt
    }

    @NonCPS
    static PipelineElement getInstance() {
        new StopElement()
    }
}
