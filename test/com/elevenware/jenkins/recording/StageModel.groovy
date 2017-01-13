package com.elevenware.jenkins.recording

class StageModel {

    private CodeBlock codeBlock
    private String name

    StageModel(String name) {
        this.name = name
        codeBlock = new CodeBlock("")
    }

    CodeBlock getCodeBlock() {
        codeBlock
    }

    def invokeDsl(String name, args) {
        codeBlock."$name"(*args)
    }

}
