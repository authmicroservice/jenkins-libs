package com.elevenware.jenkins.recording

import java.lang.reflect.Method

class NestableInvocation extends InvocationHandler{
    NestableInvocation(Method method) {
        super(method)
    }

    @Override
    def handle(CodeBlock block, Object...args) {
        Closure closure = args.find { Closure.isAssignableFrom(it.getClass())}
        closure.setDelegate(block)
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)
        closure.call()
        block."${super.name}"(*args)
    }
}
