package com.elevenware.jenkins.pipelines

class PipelineBuilderDelegate implements Serializable {

    private PipelineBuilder builder

    PipelineBuilderDelegate(PipelineBuilder builder) {
        this.builder = builder;
    }

    void flow(String flowType) {
        builder.createFlow(flowType)
    }

}
