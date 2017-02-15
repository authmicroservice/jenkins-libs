package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.CodeBlock
import com.elevenware.jenkins.recording.Invocation
import com.elevenware.jenkins.recording.StageModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

class InvocationArgumentCaptureMatcher extends BaseMatcher<CodeBlock> {

    private static final String NOT_FOUND = "was not found"
    private static final String NULL_ITEM = "No CodeBlock or StageModel was passed at all"

    private String command
    private ArgumentCapture capture
    private StringBuilder errorMessage = new StringBuilder()

    InvocationArgumentCaptureMatcher(String command, ArgumentCapture capture) {
        this.command = command
        this.capture = capture
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
        for(Invocation invocation: invocations) {
            capture.capture(invocation)
        }
        return true
    }

    @Override
    void describeTo(Description description) {

    }
}
