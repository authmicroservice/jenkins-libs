package com.elevenware.jenkins.recording

import static org.mockito.Mockito.mock

class DslDelegate {

    private Map recordings = [nodes: 1, stages: [defaultModel: new CodeBlock('default')]]

    private DslStub stub

    DslDelegate() {
        stub = mock(DslStub)
    }

    def node(Closure closure) {
        String lookupName = "node_${recordings.nodes}"
        CodeBlock stageModel = new CodeBlock(lookupName)
        closure.setDelegate(stageModel)
        def stageList = recordings['stages']
        stageList[lookupName] = stageModel
        closure.setDirective(Closure.DELEGATE_ONLY)
        closure.setProperty("STAGE_NAME", lookupName)
        closure.call()
        recordings.nodes = recordings.nodes + 1
    }

    def stage(String stageName, Closure closure) {
        String lookupName = "stage_${stageName}"
        CodeBlock stageModel = new CodeBlock(stageName)
        closure.setDelegate(stageModel)
        def stageList = recordings['stages']
        stageList[lookupName] = stageModel
        closure.setDirective(Closure.DELEGATE_ONLY)
        closure.setProperty("STAGE_NAME", lookupName)
        closure.call()
    }

    def getRecordings() {

        recordings
    }

    DslStub getStub() {
        stub
    }

    def methodMissing(String name, args) {
        def owner = this.metaClass.owner.binding.properties.variables
        String stageName = 'defaultModel'
        if(owner.containsKey('STAGE_NAME')) {
            stageName = owner.get('STAGE_NAME')
        }
        def resp = recordings.stages[stageName]."${name}"(*args)
        stub.invokeMethod(name, args)
    }

}
