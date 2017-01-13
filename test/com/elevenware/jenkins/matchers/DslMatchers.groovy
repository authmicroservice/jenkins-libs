package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.Invocation
import com.elevenware.jenkins.recording.CodeBlock
import com.elevenware.jenkins.recording.StageModel
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class DslMatchers extends BaseMatcher<StageModel>{

    private String command
    private Object[] args

    private Object current
    private Object currentActual

    DslMatchers(String command, Object...args) {
        this.command = command
        this.args = args
    }

        @Override
        boolean matches(Object item) {
            CodeBlock model = (CodeBlock) item
            List<Invocation> invocations = model.invocations.findAll { it.name == command }
            if(invocations.size() == 0) return false
            for(Invocation invocation: invocations) {
                if(assertMatch(invocation)) {
                    return true
                }
            }

            return false
        }

        private boolean assertMatch(Invocation invocation) {
            if(invocation.args.size() < args?.length) return false
            if(args?.length == 0) return true
            Iterator iter = invocation.args.iterator()
            for(Object arg: args) {
                if(!iter.hasNext()) {
                    return false
                }
                current = arg
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
            description.appendValue(current)
        }

         void describeMismatch(Object item, Description description) {
            description.appendText("was ").appendValue(currentActual);
        }

    static Matcher<StageModel> hadInvocation(String command, Object...args) {
        return new DslMatchers(command, args)
    }



}
