package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.Invocation
import com.elevenware.jenkins.recording.StageModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class DslMatchers extends BaseMatcher<StageModel>{

    private String command
    private Object[] args

    private Object current

    DslMatchers(String command, Object...args) {
        this.command = command
        this.args = args
    }

        @Override
        boolean matches(Object item) {
            StageModel model = (StageModel) item
            Invocation invocation = model.invocations.find { it.name == command }
            if(!invocation) return false
            if(invocation.args.size() < args.length) return false
            Iterator iter = invocation.args.iterator()
            for(Object arg: args) {
                if(!iter.hasNext()) {
                    return false
                }
                current = arg
                Object actualArg = iter.next()
                if(Matcher.isAssignableFrom(arg.getClass())) {
                    Matcher matcher = (Matcher) arg
                    if(!matcher.matches(actualArg)) {
                        return false
                    }
                } else {
                    if(actualArg != arg) {
                        return false
                    }
                }

            }
            return true
        }

        @Override
        void describeTo(Description description) {
            description.appendValue(current)
        }

    static Matcher<StageModel> hadInvocation(String command, Object...args) {
        return new DslMatchers(command, args)
    }



}
