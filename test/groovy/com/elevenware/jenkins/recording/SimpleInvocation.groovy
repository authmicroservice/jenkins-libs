package com.elevenware.jenkins.recording

import java.lang.reflect.Method

class SimpleInvocation extends InvocationHandler{

    SimpleInvocation(Method method) {
        super(method)
    }

    @Override
    def handle(CodeBlock block, Object...args) {
        block."${super.name}"(*args)
    }
}
