package com.elevenware.jenkins.recording

import java.lang.reflect.Method

abstract class InvocationHandler {

    protected String name
    protected def expectedArgTypes

    InvocationHandler(Method method) {
        this.name = method.name
        this.expectedArgTypes = method.parameterTypes
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
