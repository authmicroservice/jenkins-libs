package com.elevenware.jenkins.matchers

import org.junit.Test

import static com.elevenware.jenkins.matchers.MapMatchers.hasValues
import static org.hamcrest.MatcherAssert.assertThat

import static org.hamcrest.Matchers.equalTo
import static org.mockito.ArgumentMatchers.argThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class MapArgThatTests {

    @Test
    void hasValuesWorks() {

        ToBeMocked mocked = mock(ToBeMocked)
        when(mocked.methodOne(argThat(hasValues([foo: 'bar'])))).thenReturn(1)

        assertThat(mocked.methodOne([foo: 'bar']), equalTo(1))

    }

    static interface ToBeMocked {

        int methodOne(String arg)
        int methodOne(Map args)

    }

}
