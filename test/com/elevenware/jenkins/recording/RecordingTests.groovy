package com.elevenware.jenkins.recording

import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testable
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class RecordingTests {

    @Test
    void simplestPossible() {

        TestScripts scripts = testable(TestScripts)
        scripts.simplest()

        def recordings = scripts.getRecordings()

        assertNotNull recordings
        StageModel stage = recordings.stages['defaultModel']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Hello, world!"))

    }

    @Test
    void passedParams() {

        String paramToPass = "This is a string"
        TestScripts scripts = testable(TestScripts)
        scripts.passParam(paramToPass)

        def recordings = scripts.getRecordings()

        assertNotNull recordings
        StageModel stage = recordings.stages['defaultModel']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", paramToPass))

    }

    @Test
    void inAStage() {

        TestScripts scripts = testable(TestScripts)
        scripts.inStage()

        def recordings = scripts.getRecordings()

        assertNotNull recordings
        StageModel stage = recordings.stages['stage_stage1']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Hello, world!"))

    } // 084488 12135 Kate Taylor

    @Test
    void inANode() {

        TestScripts scripts = testable(TestScripts)
        scripts.inNode()

        def recordings = scripts.getRecordings()

        assertNotNull recordings

        StageModel stage = recordings.stages['node_1']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Hello, world!"))

    }


}
