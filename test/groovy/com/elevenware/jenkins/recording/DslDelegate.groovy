package com.elevenware.jenkins.recording

import static org.mockito.Mockito.mock

class DslDelegate {

    private static final String STAGE_NAME = 'STAGE_NAME'
    private static final String RECORDER_KEY = 'DSL_RECORDER'
    private PipelineRecording recording = new PipelineRecording()

    private DslStub stub

    DslDelegate() {
        stub = DslStub.INSTANCE
    }

    def node(Closure closure) {
        NodeModel nodeModel = recording.createNode()
        closure.setDelegate(nodeModel)
        closure.setDirective(Closure.DELEGATE_ONLY)
        closure.setProperty(RECORDER_KEY, nodeModel.codeBlock)
        closure.call()
    }

    def stage(String stageName, Closure closure) {
        StageModel stageModel = recording.createStage(stageName)
        closure.setDelegate(stageModel)
        closure.setDirective(Closure.DELEGATE_ONLY)
        closure.setProperty(RECORDER_KEY, stageModel.codeBlock)
        closure.call()
    }

    def getRecording() {
        recording
    }

    def methodMissing(String name, args) {
        def owner = this.metaClass.owner
        def closureBinding = owner.binding.properties.variables
        if(closureBinding.containsKey(RECORDER_KEY)) {
            CodeBlock codeBlock = closureBinding.get(RECORDER_KEY)
            codeBlock."$name"(*args)
        } else {
            recording.invokeOnDefaultStage(name, args)
        }
        DslStub.INSTANCE.invokeMethod(name, args)
    }

}
