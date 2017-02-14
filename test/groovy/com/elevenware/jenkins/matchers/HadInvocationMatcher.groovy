package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.CodeBlock
import com.elevenware.jenkins.recording.Invocation
import com.elevenware.jenkins.recording.StageModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class HadInvocationMatcher extends BaseMatcher<CodeBlock> {

    private static final String NOT_FOUND = "was not found"
    private static final String WRONG_ARGS = "was found, but not with the arguments"
    private static final String NULL_ITEM = "No CodeBlock or StageModel was passed at all"

    private String command
    private Object[] args
    private StringBuilder errorMessage = new StringBuilder()
    private Object current
    private Object currentActual

    HadInvocationMatcher(String command, Object...args) {
        this.command = command
        this.args = args
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

        errorMessage.append("${command} $WRONG_ARGS ${args}").append(System.lineSeparator()).append("Did you mean: ")
        for(Invocation invocation: invocations) {
            if(assertMatch(invocation)) {
                return true
            } else {
                errorMessage.append("${command} ${invocation.args}?").append(System.lineSeparator())
            }
        }

        return false
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
            } else {
                if(actualArg != arg) {
                    return false
                }
            }

        }
        true
    }

    @Override
    void describeTo(Description description) {
        description.appendText("an invocation of '$command' with arguments ${args}")
    }

    void describeMismatch(Object item, Description description) {
        description.appendText(errorMessage.toString())
    }
}
