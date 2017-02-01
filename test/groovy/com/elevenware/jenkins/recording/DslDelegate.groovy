package com.elevenware.jenkins.recording

class DslDelegate {

    private static final String STAGE_NAME = 'STAGE_NAME'
    private static final String RECORDER_KEY = 'DSL_RECORDER'
    private PipelineRecording recording

    private DslStub stub

    DslDelegate() {
        stub = DslStub.INSTANCE
        recording = new PipelineRecording()
        currentStage = recording.defaultStage()
    }

    private StageModel currentStage

    def getRecording() {
        recording
    }

    def stage(String stageName, Closure closure) {
        currentStage = recording.createStage(stageName)
        run(closure)
    }

    def node(Closure closure) {
        run(closure)
    }

    def run(Closure closure) {
        closure.setDelegate(this)
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.call()
    }

    def methodMissing(String name, args) {
        currentStage.invokeDsl(name, args)
        DslStub.INSTANCE.invokeMethod(name, args)
    }

}
