package com.elevenware.jenkins.recording.dsl

import com.elevenware.jenkins.recording.DslParsingException
import com.elevenware.jenkins.recording.NestableInvocation

import java.lang.reflect.Method

class DslMethodInvocationHandler {

    private Map registry

    DslMethodInvocationHandler(Class clazz) {
        registry = [:]
        for(Method method: clazz.getDeclaredMethods()) {
            def methodRegister = registry[method.name]
            if(!methodRegister) {
                methodRegister = []
                registry[method.name] = methodRegister
            }
            methodRegister << createFor(method)

        }
    }

    void registerCustomHandler(String methodName, InvocationHandler handler) {
        registry[methodName] = handler
     }

    private def createFor(Method method) {
        CustomHandler customHandlerAnnotation = method.getAnnotation(CustomHandler)
        if(customHandlerAnnotation) {
            Class customHandler = customHandlerAnnotation.value()
            return customHandler.newInstance()
        }
        for(Class type: method.getParameterTypes()) {
            if(Closure.isAssignableFrom(type)) {
                return new NestableInvocation(method)
            }
        }
        return new SimpleInvocation(method)
    }

    static DslMethodInvocationHandler forDsl() {
        new DslMethodInvocationHandler(DslStub)
    }

    def handle(CodeBlock codeBlock, String methodName, Object...args) {
        def handlers = registry[methodName]
        if(!handlers) {
            fail(methodName)
        }
        InvocationHandler handler = handlers.find { InvocationHandler h -> h.matches(args)}
        if(!handler) {
            fail(methodName, args)
        }
        handler.handle(codeBlock, *args)
    }

    private void fail(String methodName, args = null) {
        def message = ( args == null ? methodName : "${methodName} with arguments ${args*.getClass()}")
        throw new DslParsingException("Don't know how to handle ${message}")
    }
}
