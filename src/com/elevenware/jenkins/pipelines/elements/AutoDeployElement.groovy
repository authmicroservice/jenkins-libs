package com.elevenware.jenkins.pipelines.elements

import com.elevenware.jenkins.pipelines.Platform

class AutoDeployElement extends PipelineElement {

    private String environment

    AutoDeployElement(String environment) {
        this.environment = environment
    }

    @Override
    void generate(Platform platform) {

    }

}
