package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.StageModel
import org.hamcrest.Matcher

class DslMatchers {

    static Matcher<StageModel> hadInvocation(String command, Object...args) {
        return new HadInvocationMatcher(command, args)
    }

    static Matcher<CharSequence> isString(String expected) {
        return new IsStringMatcher(expected)
    }

}
