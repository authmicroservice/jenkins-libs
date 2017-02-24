package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.dsl.Invocation
import com.elevenware.jenkins.recording.dsl.StageModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class HasArgumentsMatcher extends BaseMatcher<StageModel> {

    private static final String WRONG_ARGS = "was found, but not with the arguments"

    private String command
    private Object[] args
    private StringBuilder errorMessage = new StringBuilder()
    private Object current
    private Object currentActual

    HasArgumentsMatcher(String command, Object...args) {
        this.command = command
        this.args = args
    }

    @Override
    boolean matches(Object item) {
        List<Invocation> invocations = (List) item
        errorMessage.append("${command} $WRONG_ARGS ${args}").append(System.lineSeparator()).append("Did you mean: ")
        for(Invocation invocation: invocations) {
            if(assertMatch(invocation)) {
                return true
            } else {
                errorMessage.append("${command} ${invocation.args}?").append(System.lineSeparator())
            }
        }

    }

    private boolean assertMatch(Invocation invocation) {
        if(invocation.args.size() < args?.length) return false
        if(args?.length == 0) return true
        Iterator iter = invocation.args.iterator()
        for(Object arg: args) {

            current = arg
            if(!iter.hasNext()) {
                return false
            }

            Object actualArg = iter.next()
            currentActual = actualArg
            if(Matcher.isAssignableFrom(arg.getClass())) {
                Matcher matcher = (Matcher) arg
                if(!matcher.matches(actualArg)) return false
            }
            else
            if(ArgumentCaptureWrapper.isAssignableFrom(arg.getClass())) {
                ArgumentCaptureWrapper wrapper = (ArgumentCaptureWrapper) arg
                if(!wrapper.matches(actualArg)) {
                    return false
                } else {
                    wrapper.capture(actualArg)
                }
            }
            else {
                if(actualArg != arg) {
                    return false
                }
            }

        }
        true
    }

    @Override
    void describeTo(Description description) {
        description.appendText(" with arguments ${args}")
    }

    @Override
    void describeMismatch(Object item, Description description) {
        description.appendText(errorMessage.toString())
    }
}
