package com.elevenware.jenkins.pipelines

class PipelineBuilder implements Serializable {

    private Class<Pipeline> myPipelineClass
    private Class<Platform> myPlatformClass

    void createFlow(String flowType) {
        this.myPipelineClass = Pipeline.forType(flowType)
    }

    void createPlatform(String platform) {
        this.myPlatformClass = Platform.forType(platform)
    }

    Pipeline getPipeline() {
        Platform platform = myPlatformClass.newInstance()
        myPipelineClass.newInstance(platform)
    }

}
