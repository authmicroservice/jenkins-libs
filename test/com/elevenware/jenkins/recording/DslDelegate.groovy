package com.elevenware.jenkins.recording

import static org.mockito.Mockito.mock

class DslDelegate {

    private Map recordings = [nodes: [], stages: [:]]

    private DslStub stub

    DslDelegate() {
        stub = mock(DslStub)
    }

    def node(Closure closure) {
        NodeDelegate nodeDelegate = new NodeDelegate()
        closure.setDelegate(nodeDelegate)
        def nodeList = recordings['nodes']
        nodeList << nodeDelegate
        closure.setDirective(Closure.DELEGATE_FIRST)
        closure.call()
    }

    def stage(String stageName, Closure closure) {
        StageModel stageModel = new StageModel(stageName)
        closure.setDelegate(stageModel)
        def stageList = recordings['stages']
        stageList[stageName] = stageModel
        closure.setDirective(Closure.DELEGATE_FIRST)
        closure.call()
    }

    def getRecordings() {

        recordings
    }

    DslStub getStub() {
        stub
    }

    def methodMissing(String name, args) {
        stub.invokeMethod(
                name, args
        )
    }

}
