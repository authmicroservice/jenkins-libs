package com.elevenware.jenkins.pipelines.elements

import com.elevenware.jenkins.pipelines.Platform
import com.elevenware.jenkins.pipelines.definitions.BasicDefinitions

class AutoDeployElement extends PipelineElement {

    private String environment

    AutoDeployElement(String environment) {
        this.environment = environment
    }

    @Override
    void generate(Platform platform) {
        def basic = new BasicDefinitions()

        basic.inStage('Build') {
            basic.deploy(environment)
        }
    }

}
