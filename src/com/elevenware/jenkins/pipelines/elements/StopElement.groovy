package com.elevenware.jenkins.pipelines.elements

import com.elevenware.jenkins.pipelines.Platform

class StopElement extends PipelineElement {

    StopElement() {
        super(null)
    }

    @Override
    void generate(Platform platform) {
        // nowt
    }

    static PipelineElement getInstance() {
        new StopElement()
    }
}
