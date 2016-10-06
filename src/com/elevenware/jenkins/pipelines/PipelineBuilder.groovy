package com.elevenware.jenkins.pipelines

class PipelineBuilder {

    private Pipeline myPipeline

    void createFlow(String flowType) {
        this.myPipeline = Pipeline.forType(flowType)
    }

    Pipeline getPipeline() {
        myPipeline
    }

}
