package com.elevenware.jenkins.pipelines

class PipelineBuilderDelegate implements Serializable {

    private PipelineBuilder builder

    PipelineBuilderDelegate(PipelineBuilder builder) {
        this.builder = builder;
    }

    void flow(String flowType) {
        println "RUNNING $flowType"
        builder.createFlow(flowType)
    }

    void platform(String platform) {
        builder.createPlatform(platform)
    }

    void deployStrategy(String deployStrategy) {
        builder.createDeployStrategy(deployStrategy)
    }

}
