package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.Invocation

class ArgumentCapture {

    private Class clazz
    private List invocations = []

    ArgumentCapture(Class clazz) {
        this.clazz = clazz
    }

    boolean shouldCapture(Object arg) {
        return clazz.isAssignableFrom(arg.getClass())
    }

    void capture(Object arg) {
        if(shouldCapture(arg)) {
            invocations << arg
        }
    }

    def value() {
        invocations.first()
    }

}
