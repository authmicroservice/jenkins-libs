package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.CodeBlock
import com.elevenware.jenkins.recording.StageModel
import org.hamcrest.BaseDescription
import org.hamcrest.Description
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.isA
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.MatcherAssert.assertThat

class HadInvocationMatcherTests {

    @Test
    void hadInvocationFailsToMatchIfNoMatchExists() {

        CodeBlock model = new CodeBlock()

        assertThat(model, not(hadInvocation("foo")))

    }

    @Test
    void failureProvidesCorrectExpectedValue() {

        CodeBlock model = new CodeBlock()

        StringBuilder buf = new StringBuilder()

        HadInvocationMatcher matcher = new HadInvocationMatcher("foo", "bar", "baz")

        matcher.matches(model)

        Description description = new BaseDescription() {
            @Override
            protected void append(char c) {
                buf.append c
            }
        }

        matcher.describeTo(description)
        assertThat(buf.toString(), equalTo("an invocation of 'foo' with arguments [bar, baz]"))

    }

    @Test
    void failureProvidesCorrectActualValue() {

        CodeBlock model = new CodeBlock()

        StringBuilder buf = new StringBuilder()

        HadInvocationMatcher matcher = new HadInvocationMatcher("foo")

        matcher.matches(model)

        Description description = new BaseDescription() {
            @Override
            protected void append(char c) {
                buf.append c
            }
        }

        matcher.describeMismatch(model, description)

        assertThat(buf.toString(), equalTo("foo was not found"))

    }

    @Test
    void commandButWrongArgs() {

        CodeBlock model = new CodeBlock()

        model.foo("baz")

        StringBuilder buf = new StringBuilder()

        HadInvocationMatcher matcher = new HadInvocationMatcher("foo", "bar")

        matcher.matches(model)

        Description description = new BaseDescription() {
            @Override
            protected void append(char c) {
                buf.append c
            }
        }

        matcher.describeMismatch(model, description)

        assertThat(buf.toString(), equalTo("foo was found, but not with the arguments [bar]"))

    }

    @Test
    void hadInvocationPassesIfMatchExists() {

        CodeBlock model = new CodeBlock()
        model.foo()

        assertThat(model, hadInvocation("foo"))

    }

    @Test
    void hadInvocationPassessIfArgsNotRequested() {

        CodeBlock model = new CodeBlock()
        model.foo("bar", "baz")

        assertThat(model, hadInvocation("foo"))

    }

    @Test
    void hadInvocationPassessIfArgsMactch() {

        CodeBlock model = new CodeBlock()
        model.foo("bar", "baz")

        assertThat(model, hadInvocation("foo", "bar", "baz"))

    }

    @Test
    void hadInvocationFailssIfArgsDontMactch() {

        CodeBlock model = new CodeBlock()
        model.foo("bar", "baz")

        assertThat(model, not(hadInvocation("foo", "bar", "wibble")))

    }

    @Test
    void hadInvocationPassesWithWildcard() {

        CodeBlock model = new CodeBlock()
        model.foo("bar", "baz")

        assertThat(model, hadInvocation("foo", "bar", isA(String)))

    }

    @Test
    void multipleInvocations() {

        CodeBlock model = new CodeBlock()
        model.foo("bar")
        model.foo("baz")

        assertThat(model, hadInvocation("foo", "bar"))
        assertThat(model, hadInvocation("foo", "baz"))

    }

    @Test
    void hadInvocationAcceptsStageModel() {

        StageModel stageModel = new StageModel("stage")
        CodeBlock codeBlock = stageModel.codeBlock
        codeBlock.foo()

        assertThat(stageModel, hadInvocation("foo"))

    }


}
