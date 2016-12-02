package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.StageModel
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static org.hamcrest.CoreMatchers.isA
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.MatcherAssert.assertThat

class DslMatchersTests {

    @Test
    void hadInvocationFailsToMatchIfNoMatchExists() {

        StageModel model = new StageModel()

        assertThat(model, not(hadInvocation("foo")))

    }

    @Test
    void hadInvocationPassesIfMatchExists() {

        StageModel model = new StageModel()
        model.foo()

        assertThat(model, hadInvocation("foo"))

    }

    @Test
    void hadInvocationPassessIfArgsNotRequested() {

        StageModel model = new StageModel()
        model.foo("bar", "baz")

        assertThat(model, hadInvocation("foo"))

    }

    @Test
    void hadInvocationPassessIfArgsMactch() {

        StageModel model = new StageModel()
        model.foo("bar", "baz")

        assertThat(model, hadInvocation("foo", "bar", "baz"))

    }

    @Test
    void hadInvocationFailssIfArgsDontMactch() {

        StageModel model = new StageModel()
        model.foo("bar", "baz")

        assertThat(model, not(hadInvocation("foo", "bar", "wibble")))

    }

    @Test
    void hadInvocationPassesWithWildcard() {

        StageModel model = new StageModel()
        model.foo("bar", "baz")

        assertThat(model, hadInvocation("foo", "bar", isA(String)))

    }

}
