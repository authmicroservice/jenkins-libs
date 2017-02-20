package com.elevenware.jenkins.recording

import java.util.logging.Logger

class DslDelegate {

    private static Logger LOG = Logger.getLogger(DslDelegate.name)

    private PipelineRecording recording

    private DslStub stub

    DslDelegate() {
        LOG.info("Created new DslDelegate")
        stub = DslStub.INSTANCE
        recording = new PipelineRecording()
    }

    def getRecording() {
        recording
    }

    void setRecording(PipelineRecording newRecording) {
        LOG.info("replacing pipeline recording")
        this.recording = newRecording
    }

    def stage(String stageName, Closure closure) {
        LOG.info("New stage $stageName")
        recording.createStage(stageName)
        LOG.info("CURRENT STAG $stageName")
        run(closure)
    }

    def node(Closure closure) {
        LOG.info("New node in stage ${recording.currentStage().name}")
        run(closure)
    }

    def run(Closure closure) {
        closure.setDelegate(this)
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)
        closure.call()
    }

    def methodMissing(String name, args) {
        LOG.info("Invoking DSL method $name with $args on stage ${recording.currentStage().name}")
        recording.invokeOnCurrentStage(name, args)
        DslStub.INSTANCE.invokeMethod(name, args)
    }

}
