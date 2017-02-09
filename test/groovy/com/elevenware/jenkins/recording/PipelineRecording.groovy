package com.elevenware.jenkins.recording

class PipelineRecording {

    private def stages = [:]
    private DslMethodInvocationHandler invocationHandler

    private StageModel defaultStage

    PipelineRecording() {
        invocationHandler = new DslMethodInvocationHandler(DslStub)
        defaultStage = new StageModel('defaultStage', invocationHandler)
    }

    StageModel defaultStage() {
        defaultStage
    }

    StageModel getStage(String stageName) {
        stages[stageName]
    }

    StageModel createStage(String stageName) {
        StageModel model = new StageModel(stageName, invocationHandler)
        stages[stageName] = model
        model

    }

    def invokeOnDefaultStage(String name, args) {
        defaultStage.invokeDsl(name, args)
    }

}
