package com.elevenware.jenkins.recording

class PipelineRecording {

    private def stages = [:]

    private StageModel defaultStage = new StageModel('defaultStage')

    StageModel defaultStage() {
        defaultStage
    }

    StageModel getStage(String stageName) {
        stages[stageName]
    }

    StageModel createStage(String stageName) {
        StageModel model = new StageModel(stageName)
        stages[stageName] = model
        model

    }

    def invokeOnDefaultStage(String name, args) {
        defaultStage.invokeDsl(name, args)
    }

}
