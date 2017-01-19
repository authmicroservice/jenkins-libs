package com.elevenware.jenkins.matchers

import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.isString
import static org.junit.Assert.assertThat

class UtililtyMatchersTests {

    @Test
    void groovyStringMatcherWorksWithJavaStrings() {

        String expected = 'this is what I expect'
        String actual = 'this is what I expect'

        assertThat(actual, isString(expected))

    }

    @Test
    void groovyStringMatcherWorksWithGStringExpected() {

        String suffix = 'expect'
        String expected = "this is what I $suffix"
        String actual = 'this is what I expect'

        assertThat(actual, isString(expected))

    }

    @Test
    void groovyStringMatcherWorksWithGStringActual() {

        String suffix = 'expect'
        String expected = 'this is what I expect'
        String actual = "this is what I $suffix"

        assertThat(actual, isString(expected))

    }

    @Test
    void groovyStringMatcherWorksWithGStrings() {

        String suffix = 'expect'
        String expected = "this is what I $suffix"
        String actual = "this is what I $suffix"

        assertThat(actual, isString(expected))

    }

}
