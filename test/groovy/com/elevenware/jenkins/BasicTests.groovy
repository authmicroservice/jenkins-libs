package com.elevenware.jenkins

import com.elevenware.jenkins.pipelines.Pipeline
import org.junit.Assert
import org.junit.Test

import static org.junit.Assert.assertNotNull

class BasicTests {

    @Test
    void something() {
        Class pipeline = Pipeline.forType("github")
        assertNotNull pipeline
    }

}
