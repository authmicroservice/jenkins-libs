package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.definitions.GenericBuildSteps

class BasicPlatform extends Platform {

    private def buildSteps

    BasicPlatform() {
        this.buildSteps = new GenericBuildSteps()
    }

    @Override
    void build() {
        buildSteps.build()
    }

    @Override
    void test() {
        buildSteps.test()
    }

    @Override
    void publish() {
        buildSteps.publish()
    }
}
