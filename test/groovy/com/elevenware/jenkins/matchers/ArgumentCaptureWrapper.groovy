package com.elevenware.jenkins.matchers

class ArgumentCaptureWrapper {

    private ArgumentCapture capture

    ArgumentCaptureWrapper(ArgumentCapture capture) {
        this.capture = capture
    }

    boolean matches(Object arg) {
        return capture.shouldCapture(arg)
    }

    void capture(Object arg) {
        capture.capture(arg)
    }

}
