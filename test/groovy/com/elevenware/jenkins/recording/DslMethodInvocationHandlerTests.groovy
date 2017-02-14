package com.elevenware.jenkins.recording

import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.isA
import static org.junit.Assert.assertThat

class DslMethodInvocationHandlerTests {

    private DslMethodInvocationHandler handler = new DslMethodInvocationHandler(SimpleInterface)

    @Test(expected = DslParsingException)
    void methodNotFound() {

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "nonExistent", "foo")

    }

    @Test
    void simpleCaseWorks() {

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "doSomething", "foo")

        assertThat(codeBlock, hadInvocation("doSomething", "foo"))

    }

    @Test
    void multipleArgs() {

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "doBoth", "foo", "bar")

        assertThat(codeBlock, hadInvocation("doBoth", "foo", "bar"))

    }

    @Test
    void polymorphismOfSimpleArgs() {

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "doSomethingElse", "foo")

        assertThat(codeBlock, hadInvocation("doSomethingElse", "foo"))

    }

    @Test
    void closuresWork() {

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

        CodeBlock codeBlock = new CodeBlock()

        def closure = {
            doSomething "foo"
        }

        handler.handle(codeBlock, "doAllThis", "bar", closure)

        assertThat(codeBlock, hadInvocation("doAllThis", "bar", isA(Closure)))
        assertThat(codeBlock, hadInvocation("doSomething", 'foo'))

    }

    @Test
    void addCustomHandler() {

        DslMethodInvocationHandler myHandler = new DslMethodInvocationHandler(SimpleInterface)

        StringBuilder buf = new StringBuilder()

        myHandler.registerCustomHandler("notOnInterface", new InvocationHandler("notOnInterface", String) {

            @Override
            def handle(CodeBlock block, Object... args) {
                buf.append(args[0])
            }
        })

        CodeBlock codeBlock = new CodeBlock()

        myHandler.handle(codeBlock, "notOnInterface", "foo")

        assertThat(buf.toString(), equalTo("foo"))

    }

}

interface SimpleInterface {

    void doSomething(String s)
    void doBoth(String a, String b)
    void doSomethingElse(Object args)
    void doTheseThings(Closure closure)
    void doAllThis(String s, Closure closure)

}