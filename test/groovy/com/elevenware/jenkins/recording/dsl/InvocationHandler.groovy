package com.elevenware.jenkins.recording.dsl

import com.elevenware.jenkins.recording.dsl.CodeBlock

import java.lang.reflect.Method

abstract class InvocationHandler {

    protected String name
    protected def expectedArgTypes

    InvocationHandler(String name, Class...argTypes) {
        this.name = name
        this.expectedArgTypes = argTypes
    }

    InvocationHandler(Method method) {
        this(method.name, method.parameterTypes)
    }

    boolean matches(Object...args) {
        Iterator<Class> expectedIter = expectedArgTypes.iterator()
        for(Object arg: args) {
            Class actual = arg.getClass()
            Class expected = expectedIter.next()
            if(!expected.isAssignableFrom(actual)) {
                return false
            }
        }
        return true
    }

    abstract def handle(CodeBlock block, Object...args)

}
