package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.definitions.GithubPipelineDefinition
import com.elevenware.jenkins.recording.CodeBlock
import com.elevenware.jenkins.recording.PipelineRecording
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testable
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNotNull

import static org.junit.Assert.*

class SimplePipelineTests {

    @Test
    void runSimplePipeline() {

        String message = "Hello, world!"

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)

        pipeline.build([message: message])

        def recordings = pipeline.getRecordings()

        assertNotNull recordings
        CodeBlock stage = recordings.stages['stage_build']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", message))

    }

    @Test
    void correctStagesExist() {

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)
        pipeline.build([:])

        PipelineRecording recording = pipeline.recording

        assertEquals(2, recording.stages.size())

    }

}
