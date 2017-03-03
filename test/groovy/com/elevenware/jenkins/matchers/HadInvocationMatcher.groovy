package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.dsl.CodeBlock
import com.elevenware.jenkins.recording.dsl.Invocation
import com.elevenware.jenkins.recording.dsl.StageModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class HadInvocationMatcher extends BaseMatcher<CodeBlock> {

    private static final String NOT_FOUND = "was not found"
    private static final String NULL_ITEM = "No CodeBlock or StageModel was passed at all"
    private static final Matcher NOOP_MATCHER = new NoopMatcher()

    private String command
    private StringBuilder errorMessage = new StringBuilder()
    private Matcher argsMatcher = NOOP_MATCHER
    private Matcher countMatcher = NOOP_MATCHER


    HadInvocationMatcher(String command) {
        this.command = command
    }

    @Override
    boolean matches(Object item) {
        if(!item) {
            errorMessage.append(NULL_ITEM)
            return false
        }
        CodeBlock model
        if(StageModel.isAssignableFrom(item.getClass())) {
            model = ((StageModel) item).codeBlock
        } else {
            model = (CodeBlock) item
        }
        List<Invocation> invocations = model.invocations.findAll { it.name == command }
        if(invocations.size() == 0) {
            errorMessage.append("${command} ${NOT_FOUND}")
            return false
        }

        boolean hasArguments = argsMatcher.matches(invocations)
        boolean countsProperly = countMatcher.matches(invocations)

        return hasArguments && countsProperly
    }

    HadInvocationMatcher withArgs(Object...args) {
        this.argsMatcher = new HasArgumentsMatcher(this.command, args)
        return this
    }

    HadInvocationMatcher exactly(int times) {
        this.countMatcher = new ExactCountMatcher(this.command, times)
        return this
    }

    @Override
    void describeTo(Description description) {
        description.appendText("an invocation of '$command'")
        argsMatcher.describeTo(description)
    }

    void describeMismatch(Object item, Description description) {
        description.appendText(errorMessage.toString())
        argsMatcher.describeMismatch(item, description)
    }

    private static class NoopMatcher extends BaseMatcher {

        @Override
        boolean matches(Object item) {
            return true
        }

        @Override
        void describeTo(Description description) {

        }

        @Override
        void describeMismatch(Object item, Description description) {

        }
    }
}
