package com.elevenware.jenkins.recording

class StageModel {

    private CodeBlock codeBlock
    private DslMethodInvocationHandler invocationHandler
    private String name

    StageModel(String name, DslMethodInvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler
        this.name = name
        codeBlock = new CodeBlock()
    }

    String getName() {
        name
    }

    CodeBlock getCodeBlock() {
        codeBlock
    }

    def invokeDsl(String name, args) {
        invocationHandler.handle(codeBlock, name, args)
    }

}
