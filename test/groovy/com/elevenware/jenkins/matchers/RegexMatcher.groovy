package com.elevenware.jenkins.matchers

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

import java.util.regex.Pattern

class RegexMatcher extends TypeSafeMatcher<String> {

    private final String regex;

    RegexMatcher(String regex) {
        this.regex = regex
    }

    static org.hamcrest.Matcher<java.lang.String> matches(String regex) {
        return new RegexMatcher(regex)
    }

    @Override
    public boolean matchesSafely(String item) {
        return Pattern.compile(this.regex).matcher(item).find()
    }

    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("was \"").appendText(item).appendText("\"")
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a string that matches regex: ")
                .appendText(this.regex)
    }
}
