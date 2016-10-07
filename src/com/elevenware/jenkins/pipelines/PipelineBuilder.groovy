package com.elevenware.jenkins.pipelines

class PipelineBuilder implements Serializable {

//    private Pipeline myPipeline

    void createFlow(String flowType) {
//        this.myPipeline = Pipeline.forType(flowType)
    }

    Pipeline getPipeline() {
        myPipeline
    }

}
