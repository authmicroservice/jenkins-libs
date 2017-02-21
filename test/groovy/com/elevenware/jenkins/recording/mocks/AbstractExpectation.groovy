package com.elevenware.jenkins.recording.mocks

abstract class AbstractExpectation implements Expectation {

    private Response response = new Response()

    Expectation thenRespond(int response) {
        this.response.responseCode = response
        this
    }

    Expectation withStdout(String stdout) {
        response.stdout = stdout
        this
    }

    Response getResponse() {
        response
    }
}
