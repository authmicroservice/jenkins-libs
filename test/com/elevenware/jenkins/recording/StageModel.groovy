package com.elevenware.jenkins.recording

class StageModel {

    String name
    List invocations = []

    StageModel(String name) {
        this.name = name
    }

    def methodMissing(String name, args) {
        invocations << new Invocation(name, args)
    }

}
