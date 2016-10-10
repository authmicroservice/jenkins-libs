package com.elevenware.jenkins.pipelines.elements

import com.elevenware.jenkins.pipelines.Platform
import com.elevenware.jenkins.pipelines.definitions.BasicDefinitions

class BuildTestPublishElement extends PipelineElement {

    @Override
    void generate(Platform platform) {
        def basic = new BasicDefinitions()

        basic.inStage('Build') {
            basic.checkout()
        }

    }

}
