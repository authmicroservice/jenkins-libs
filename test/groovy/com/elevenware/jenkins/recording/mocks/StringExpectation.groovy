package com.elevenware.jenkins.recording.mocks

class StringExpectation extends AbstractExpectation {

    String command

    StringExpectation(String command) {
        this.command = command
    }

    @Override
    boolean matches(String cmd) {
        return cmd == command
    }

}
