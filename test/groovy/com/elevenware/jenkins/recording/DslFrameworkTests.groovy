package com.elevenware.jenkins.recording

import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testableSnippet
import static org.junit.Assert.assertThat
import static org.mockito.Matchers.isA
import static org.mockito.Mockito.when

class DslFrameworkTests {

    @Test
    void echoCallWithHardArgs() {

        def block = testableSnippet {
            echo "hello"
        }

        assertThat(block, hadInvocation("echo", "hello"))

    }

    @Test
    void closureCall() {

        def block = testableSnippet {
            withMaven {
                echo "A maven step"
            }
        }

        assertThat(block, hadInvocation("withMaven", isA(Closure)))
        Invocation withMaven = block.getInvocation("withMaven")

        block = testableSnippet(withMaven.args.first())

        assertThat(block, hadInvocation("echo", "A maven step"))

    }

    @Test
    void inlineArgsAndClosure() {

        def block = testableSnippet {
            withMaven(version: '3.3') {
                echo "A maven step"
            }
        }

        assertThat(block, hadInvocation("withMaven", isA(Closure)))
        Invocation withMaven = block.getInvocation("withMaven")

        def withMavenClosure = withMaven.args[1]

        block = testableSnippet( withMavenClosure )

        assertThat(block, hadInvocation("echo", "A maven step"))

    }

    @Test(expected = MissingMethodException)
    void methodsMustExistOnDslStub() {

        testableSnippet {
            "method_${System.currentTimeMillis()}_foo"()
        }

    }

    @Test
    void stubbingResponses() {

        when(DslStub.INSTANCE.libraryResource("foo")).thenReturn("bar")

        def block = testableSnippet {
            def foo = libraryResource('foo')
            echo foo
        }

        assertThat(block, hadInvocation("echo", 'bar'))

    }

}
