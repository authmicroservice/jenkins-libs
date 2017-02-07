package com.elevenware.jenkins.recording

import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static org.hamcrest.CoreMatchers.isA
import static org.junit.Assert.assertThat

class DslMethodInvocationHandlerTests {

    @Test
    void simpleCaseWorks() {

        DslMethodInvocationHandler handler = new DslMethodInvocationHandler(SimpleInterface)

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "doSomething", "foo")

        assertThat(codeBlock, hadInvocation("doSomething", "foo"))

    }

    @Test
    void multipleArgs() {

        DslMethodInvocationHandler handler = new DslMethodInvocationHandler(SimpleInterface)

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "doBoth", "foo", "bar")

        assertThat(codeBlock, hadInvocation("doBoth", "foo", "bar"))

    }

    @Test
    void polymorphismOfSimpleArgs() {

        DslMethodInvocationHandler handler = new DslMethodInvocationHandler(SimpleInterface)

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "doSomethingElse", "foo")

        assertThat(codeBlock, hadInvocation("doSomethingElse", "foo"))

    }

    @Test
    void closuresWork() {

        DslMethodInvocationHandler handler = new DslMethodInvocationHandler(SimpleInterface)

        CodeBlock codeBlock = new CodeBlock()

        def closure = {
            doSomething "foo"
        }

        handler.handle(codeBlock, "doTheseThings", closure)

        assertThat(codeBlock, hadInvocation("doTheseThings", isA(Closure)))
        assertThat(codeBlock, hadInvocation("doSomething", 'foo'))

    }

    @Test
    void closureWithArgs() {

        DslMethodInvocationHandler handler = new DslMethodInvocationHandler(SimpleInterface)

        CodeBlock codeBlock = new CodeBlock()

        def closure = {
            doSomething "foo"
        }

        handler.handle(codeBlock, "doAllThis", "bar", closure)

        assertThat(codeBlock, hadInvocation("doAllThis", "bar", isA(Closure)))
        assertThat(codeBlock, hadInvocation("doSomething", 'foo'))

    }

}

interface SimpleInterface {

    void doSomething(String s)
    void doBoth(String a, String b)
    void doSomethingElse(Object args)
    void doTheseThings(Closure closure)
    void doAllThis(String s, Closure closure)

}