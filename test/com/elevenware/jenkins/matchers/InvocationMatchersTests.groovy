package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.CodeBlock
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static org.hamcrest.CoreMatchers.isA
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.MatcherAssert.assertThat

class InvocationMatchersTests {

    @Test
    void hadInvocationFailsToMatchIfNoMatchExists() {

        CodeBlock model = new CodeBlock("default")

        assertThat(model, not(hadInvocation("foo")))

    }

    @Test
    void hadInvocationPassesIfMatchExists() {

        CodeBlock model = new CodeBlock("default")
        model.foo()

        assertThat(model, hadInvocation("foo"))

    }

    @Test
    void hadInvocationPassessIfArgsNotRequested() {

        CodeBlock model = new CodeBlock("default")
        model.foo("bar", "baz")

        assertThat(model, hadInvocation("foo"))

    }

    @Test
    void hadInvocationPassessIfArgsMactch() {

        CodeBlock model = new CodeBlock("default")
        model.foo("bar", "baz")

        assertThat(model, hadInvocation("foo", "bar", "baz"))

    }

    @Test
    void hadInvocationFailssIfArgsDontMactch() {

        CodeBlock model = new CodeBlock("default")
        model.foo("bar", "baz")

        assertThat(model, not(hadInvocation("foo", "bar", "wibble")))

    }

    @Test
    void hadInvocationPassesWithWildcard() {

        CodeBlock model = new CodeBlock("default")
        model.foo("bar", "baz")

        assertThat(model, hadInvocation("foo", "bar", isA(String)))

    }

    @Test
    void multipleInvocations() {

        CodeBlock model = new CodeBlock("")
        model.foo("bar")
        model.foo("baz")

        assertThat(model, hadInvocation("foo", "bar"))
        assertThat(model, hadInvocation("foo", "baz"))

    }

}
