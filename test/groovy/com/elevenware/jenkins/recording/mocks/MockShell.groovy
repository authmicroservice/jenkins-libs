package com.elevenware.jenkins.recording.mocks

import java.util.regex.Pattern

class MockShell {

    private List expectations = []
    private Expectation defaultExpectation

    MockShell() {
        this(new NoopExpectation())
    }

    MockShell(Expectation defaultExpectation) {
        this.defaultExpectation = defaultExpectation
    }

    Expectation whenCommand(CharSequence command) {
        Expectation expectation = new StringExpectation(command)
        expectations << expectation
        expectation
    }

    Expectation whenCommand(Pattern regex) {
        Expectation expectation = new RegexExpectation(regex)
        expectations << expectation
        expectation
    }

    Response invoke(String command) {

        Expectation theExpectation =  expectations.find { Expectation expectation -> expectation.matches(command) }
        if(!theExpectation) {
            theExpectation = this.defaultExpectation
        }
        return theExpectation.response

    }

}
