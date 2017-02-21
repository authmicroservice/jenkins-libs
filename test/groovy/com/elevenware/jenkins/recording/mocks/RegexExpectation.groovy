package com.elevenware.jenkins.recording.mocks

import java.util.regex.Pattern

class RegexExpectation extends AbstractExpectation {

    private Pattern pattern

    RegexExpectation(Pattern pattern) {
        this.pattern = pattern
    }

    @Override
    boolean matches(String cmd) {
        return pattern.matcher(cmd).matches()
    }
}
