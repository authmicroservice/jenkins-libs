package com.elevenware.jenkins.matchers

import org.hamcrest.BaseMatcher
import org.hamcrest.Description

class ExactCountMatcher extends BaseMatcher {

    private String command
    private int count

    ExactCountMatcher(String command, int count) {
        this.command = command
        this.count = count
    }

    @Override
    boolean matches(Object item) {
        return false
    }

    @Override
    void describeTo(Description description) {

    }
}
