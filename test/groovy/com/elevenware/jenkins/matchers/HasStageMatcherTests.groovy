package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.PipelineRecording
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hasStage
import static com.elevenware.jenkins.matchers.DslMatchers.inFirstPosition
import static com.elevenware.jenkins.matchers.DslMatchers.inPosition
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.MatcherAssert.assertThat

class HasStageMatcherTests {

    @Test
    void hasStageWorks(){

        PipelineRecording recording = new PipelineRecording()

        recording.createStage("first")

        assertThat(recording, hasStage("first"))
    }

    @Test
    void failsCorrectly() {

        PipelineRecording recording = new PipelineRecording()

        assertThat(recording, not(hasStage("first")))

    }

    @Test
    void specificPositionInPipeline() {

        PipelineRecording recording = new PipelineRecording()

        recording.createStage("first")
        recording.createStage("second")
        recording.createStage("third")
        recording.createStage("fourth")

        assertThat(recording, hasStage("first", inPosition(0)))
        assertThat(recording, hasStage("second", inPosition(1)))
        assertThat(recording, hasStage("fourth", inPosition(3)))

    }

    @Test
    void firstPositionWorks() {

        PipelineRecording recording = new PipelineRecording()

        recording.createStage("first")
        recording.createStage("second")
        recording.createStage("third")
        recording.createStage("fourth")

        assertThat(recording, hasStage("first", inFirstPosition()))

    }

}
