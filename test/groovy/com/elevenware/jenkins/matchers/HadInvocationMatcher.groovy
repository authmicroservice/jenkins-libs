package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.dsl.CodeBlock
import com.elevenware.jenkins.recording.dsl.Invocation
import com.elevenware.jenkins.recording.dsl.StageModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class HadInvocationMatcher extends BaseMatcher<CodeBlock> {

    private static final String NOT_FOUND = "was not found"
    private static final String WRONG_ARGS = "was found, but not with the arguments"
    private static final String NULL_ITEM = "No CodeBlock or StageModel was passed at all"
    private static final Matcher NOOP_MATCHER = new NoopMatcher()

    private String command
    private Object[] args
    private StringBuilder errorMessage = new StringBuilder()
    private Object current
    private Object currentActual
    private Matcher delegate = NOOP_MATCHER



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

//        errorMessage.append("${command} $WRONG_ARGS ${args}").append(System.lineSeparator()).append("Did you mean: ")
//        for(Invocation invocation: invocations) {
//            if(assertMatch(invocation)) {
//                return true
//            } else {
//                errorMessage.append("${command} ${invocation.args}?").append(System.lineSeparator())
//            }
//        }

        boolean hasArguments = delegate.matches(invocations)

        return hasArguments
    }

    HadInvocationMatcher withArgs(Object...args) {
        this.delegate = new HasArgumentsMatcher(this.command, args)
        return this
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
        description.appendText("an invocation of '$command'")
        delegate.describeTo(description)
    }

    void describeMismatch(Object item, Description description) {
        description.appendText(errorMessage.toString())
        delegate.describeMismatch(item, description)
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
