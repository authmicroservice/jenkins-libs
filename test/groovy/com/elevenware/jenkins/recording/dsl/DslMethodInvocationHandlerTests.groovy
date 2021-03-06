package com.elevenware.jenkins.recording.dsl

import com.elevenware.jenkins.recording.DslParsingException
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

        assertThat(codeBlock, hadInvocation("doSomething").withArgs("foo"))

    }

    @Test
    void multipleArgs() {

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "doBoth", "foo", "bar")

        assertThat(codeBlock, hadInvocation("doBoth").withArgs("foo", "bar"))

    }

    @Test
    void polymorphismOfSimpleArgs() {

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "doSomethingElse", "foo")

        assertThat(codeBlock, hadInvocation("doSomethingElse").withArgs("foo"))

    }

    @Test
    void closuresWork() {

        CodeBlock codeBlock = new CodeBlock()

        def closure = {
            doSomething "foo"
        }

        handler.handle(codeBlock, "doTheseThings", closure)

        assertThat(codeBlock, hadInvocation("doTheseThings").withArgs(isA(Closure)))
        assertThat(codeBlock, hadInvocation("doSomething").withArgs('foo'))

    }

    @Test
    void closureWithArgs() {

        CodeBlock codeBlock = new CodeBlock()

        def closure = {
            doSomething "foo"
        }

        handler.handle(codeBlock, "doAllThis", "bar", closure)

        assertThat(codeBlock, hadInvocation("doAllThis").withArgs("bar", isA(Closure)))
        assertThat(codeBlock, hadInvocation("doSomething").withArgs('foo'))

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

    @Test
    void customHandlerAnnotationWorks() {

        CodeBlock codeBlock = new CodeBlock()

        handler.handle(codeBlock, "customHandler", "foo")

        assertThat(codeBlock, hadInvocation("wibble").withArgs("foo"))

    }

}

interface SimpleInterface {

    void doSomething(String s)
    void doBoth(String a, String b)
    void doSomethingElse(Object args)
    void doTheseThings(Closure closure)
    void doAllThis(String s, Closure closure)
    @CustomHandler(SimpleHandler)
    void customHandler(String s)

}

class SimpleHandler extends InvocationHandler {

    SimpleHandler() {
        super("customHandler", String)
    }

    @Override
    def handle(CodeBlock block, Object... args) {
        block."wibble"(*args)
    }
}