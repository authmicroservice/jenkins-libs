package com.elevenware.jenkins.recording.mocks

interface Expectation {

    boolean matches(String cmd)
    Response getResponse()

}
