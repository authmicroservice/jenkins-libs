package com.elevenware.jenkins.recording

import org.mockito.Mockito

class NodeDelegate {

    private DslStub stub = Mockito.mock(DslStub)

    void stage(Closure closure) {
        closure.setDelegate(this)
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)
        closure.call()
    }

    DslStub getStub() {
        stub
    }

    def methodMissing(String name, args) {
        stub.invokeMethod(name, args)
    }

}
