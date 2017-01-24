package com.elevenware.jenkins.recording

class SnippetDelegate {

    private static final String STAGE_NAME = 'STAGE_NAME'
    private static final String RECORDER_KEY = 'DSL_RECORDER'

    private PipelineRecording recording = new PipelineRecording()

    def getRecording() {
        recording
    }

    def methodMissing(String name, args) {
        recording.invokeOnDefaultStage(name, args)
        DslStub.INSTANCE.invokeMethod(name, args)
    }

}
