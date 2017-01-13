package com.elevenware.jenkins.recording

class PipelineRecording {

    private def stages = [:]
    private def nodes = []

    private StageModel defaultStage = new StageModel('defaultStage')

    StageModel defaultStage() {
        defaultStage
    }

    StageModel getStage(String stageName) {
        stages[stageName]
    }

    NodeModel getNode(int index) {
        nodes.get(index)
    }

    StageModel createStage(String stageName) {
        StageModel model = new StageModel(stageName)
        stages[stageName] = model
        model

    }

    NodeModel createNode() {
        NodeModel model = new NodeModel()
        nodes << model
        model
    }

    def invokeOnDefaultStage(String name, args) {
        defaultStage.invokeDsl(name, args)
    }

}
