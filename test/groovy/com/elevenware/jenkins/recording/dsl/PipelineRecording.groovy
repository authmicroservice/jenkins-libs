package com.elevenware.jenkins.recording.dsl

import com.elevenware.jenkins.recording.StageCreationEvent
import com.elevenware.jenkins.recording.events.DslEvent
import com.elevenware.jenkins.recording.events.DslEventListener
import com.elevenware.jenkins.recording.events.EventBroker

import java.util.logging.Logger

class PipelineRecording implements DslEventListener {

    private static final Logger LOG = Logger.getLogger(PipelineRecording.name)

    private def stages = [:]
    private DslMethodInvocationHandler invocationHandler

    private StageModel defaultStage
    private StageModel currentStage

    PipelineRecording() {
        invocationHandler = new DslMethodInvocationHandler(DslStub)
//        invocationHandler.registerCustomHandler("stage", new StageInvocationHandler())
        defaultStage = new StageModel('defaultStage', invocationHandler)
        currentStage = defaultStage
        EventBroker.instance.registerListener(this)
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

    Map getStages() {
        Collections.unmodifiableMap(stages)
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

    @Override
    void doReceive(DslEvent event) {

        handle(event)

    }

    private void handle(StageCreationEvent event) {
        createStage(event.stageName)
    }

    private void handle(DslEvent event){
        // NOOP
    }


}
