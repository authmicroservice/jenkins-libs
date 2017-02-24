package com.elevenware.jenkins.recording.dsl

import com.elevenware.jenkins.recording.DslParsingException
import org.junit.After
import org.junit.Test
import org.mockito.Mockito

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testableSnippet
import static org.hamcrest.CoreMatchers.isA
import static org.junit.Assert.assertThat
//import static org.mockito.Matchers.isA
import static org.mockito.Mockito.when

class DslFrameworkTests {

    @Test
    void echoCallWithHardArgs() {

        def block = testableSnippet {
            echo "hello"
        }

        assertThat(block, hadInvocation("echo").withArgs("hello"))

    }

    @Test
    void closureCall() {

        def block = testableSnippet {
            withMaven {
                echo "A maven step"
            }
        }

        assertThat(block, hadInvocation("withMaven").withArgs(isA(Closure)))
        Invocation withMaven = block.getInvocation("withMaven")

        block = testableSnippet(withMaven.args.first())

        assertThat(block, hadInvocation("echo").withArgs("A maven step"))

    }

    @Test
    void inlineArgsAndClosure() {

        def block = testableSnippet {
            withMaven(version: '3.3') {
                echo "A maven step"
            }
        }

        assertThat(block, hadInvocation("withMaven").withArgs(isA(Map), isA(Closure)))
        Invocation withMaven = block.getInvocation("withMaven")

        def withMavenClosure = withMaven.args[1]

        block = testableSnippet( withMavenClosure )

        assertThat(block, hadInvocation("echo").withArgs("A maven step"))

    }

    @Test(expected = DslParsingException)
    void methodsMustExistOnDslStub() {

        testableSnippet {
            "method_${System.currentTimeMillis()}_foo"()
        }

    }

    @Test
    void stubbingResponses() {

        when(DslStub.INSTANCE.libraryResource('foo')).thenReturn('bar')

        def block = testableSnippet {
            def foo = libraryResource('foo')
            echo foo
        }

        assertThat(block, hadInvocation("echo").withArgs('bar'))

    }

    @Test
    void nestedClosures() {

        def block = testableSnippet {
            node {
                    withMaven {
                        echo "A maven step"
                    }
            }
        }

        assertThat(block, hadInvocation("withMaven").withArgs(isA(Closure)))
        Invocation withMaven = block.getInvocation("withMaven")

        block = testableSnippet(withMaven.args.first())

        assertThat(block, hadInvocation("echo").withArgs("A maven step"))

    }

    @After
    void setup() {
        Mockito.reset(DslStub.INSTANCE);
    }

}
