package com.elevenware.jenkins.recording

class CodeBlock {

    private String name
    private List invocations = []
    private CodeBlock innerBlock

    CodeBlock(String name) {
        this(name, null)
    }

    CodeBlock(String name, CodeBlock innerBlock) {
        this.name = name
        this.innerBlock = innerBlock
    }

    def methodMissing(String name, args) {
        invocations << new Invocation(name, args)
    }

    CodeBlock getInnerBlock() {
        return innerBlock
    }

    def stage(String name, Closure closure) {
        String lookupName = "stage_${name}"
        CodeBlock stageModel = new CodeBlock(name)
        closure.setDelegate(stageModel)
        this.innerBlock = stageModel
        closure.setDirective(Closure.DELEGATE_ONLY)
        closure.setProperty("STAGE_NAME", lookupName)
        closure.call()
    }

}
