package com.elevenware.jenkins.recording.dsl

class CodeBlock {

    private List invocations = []

    def methodMissing(String name, args) {
        invocations << new Invocation(name, args)
    }

    List<Invocation> getInvocations() {
        invocations
    }

    List<Invocation> getInvocations(String name) {
        invocations.findAll { it.name == name }
    }

    Invocation getInvocation(String name) {
        getInvocations(name).first()
    }

}
