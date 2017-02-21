package com.elevenware.jenkins.recording.mocks

class NoopExpectation extends AbstractExpectation {

    NoopExpectation() {
        response.responseCode = 0
    }

    @Override
    boolean matches(String cmd) {
        return true
    }

}
