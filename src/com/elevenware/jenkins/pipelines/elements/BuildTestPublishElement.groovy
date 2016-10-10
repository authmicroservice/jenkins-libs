package com.elevenware.jenkins.pipelines.elements

import com.elevenware.jenkins.pipelines.definitions.BasicDefinitions

class BuildTestPublishElement extends PipelineElement {

    @Override
    void generate() {
        def definitions = new BasicDefinitions()
        definitions.checkout()
    }
}
