package com.elevenware.jenkins.recording

import com.kenai.jffi.Closure

class StageInvocationHandler extends InvocationHandler {

    StageInvocationHandler() {
        super("stage", String, Closure)
    }

    @Override
    def handle(CodeBlock block, Object... args) {
        def stageName = args[0]
        EventBroker.instance.notifyListeners(new StageCreationEvent(stageName))
    }
}
