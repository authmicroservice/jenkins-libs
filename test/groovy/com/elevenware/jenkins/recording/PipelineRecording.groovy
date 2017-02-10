package com.elevenware.jenkins.recording

import java.util.logging.Logger

class PipelineRecording {

    private static final Logger LOG = Logger.getLogger(PipelineRecording.name)

    private def stages = [:]
    private DslMethodInvocationHandler invocationHandler

    private StageModel defaultStage
    private StageModel currentStage

    PipelineRecording() {
        invocationHandler = new DslMethodInvocationHandler(DslStub)
        defaultStage = new StageModel('defaultStage', invocationHandler)
        currentStage = defaultStage
    }

    StageModel defaultStage() {
        defaultStage
    }

    StageModel currentStage() {
        currentStage
    }

    StageModel getStage(String stageName) {
        stages[stageName]
    }

    StageModel createStage(String stageName) {
        LOG.info("Creating new stage $stageName")
        StageModel model = new StageModel(stageName, invocationHandler)
        stages[stageName] = model
        currentStage = model
        model

    }

    def invokeOnCurrentStage(String name, args) {
        LOG.info("Invoking ${name} with ${args} on stage ${currentStage.name}")
        currentStage.invokeDsl(name, args)
    }
}
