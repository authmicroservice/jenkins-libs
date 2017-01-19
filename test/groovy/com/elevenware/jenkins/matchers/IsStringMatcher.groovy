package com.elevenware.jenkins.matchers

import org.hamcrest.BaseMatcher
import org.hamcrest.Description

class IsStringMatcher extends BaseMatcher<CharSequence> {

    private String expected

    IsStringMatcher(String expected) {
        this.expected = expected
    }

    @Override
    boolean matches(Object o) {
        String actual = String.valueOf(o)
        return expected.equals(actual)
    }

    @Override
    void describeTo(Description description) {

    }
}
