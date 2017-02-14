package com.elevenware.jenkins.recording

import com.kenai.jffi.Closure

class StageInvocationHandler extends InvocationHandler {

    private PipelineRecording recording

    StageInvocationHandler(PipelineRecording recording) {
        super("stage", String, Closure)
        this.recording = recording
    }

    @Override
    def handle(CodeBlock block, Object... args) {
        def stageName = args[0]
        println "STAGE $stageName"
        recording.createStage(stageName)
    }
}
