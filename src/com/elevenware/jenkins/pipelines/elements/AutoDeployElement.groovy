package com.elevenware.jenkins.pipelines.elements

import com.elevenware.jenkins.pipelines.Platform
import com.elevenware.jenkins.pipelines.definitions.BasicDefinitions

class AutoDeployElement extends PipelineElement implements Serializable {

    private String environment

    AutoDeployElement(String environment, PipelineElement next) {
        super(next)
        this.environment = environment
    }

    @Override
    void generate(Platform platform) {
        def basic = new BasicDefinitions()

        basic.inStage("Deploy ${environment}") {
            basic.deploy(environment)
        }
    }

}
