package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.definitions.GithubPipelineDefinition
import com.elevenware.jenkins.recording.CodeBlock
import com.elevenware.jenkins.recording.PipelineRecording
import com.elevenware.jenkins.recording.StageModel
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

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)

        pipeline.build([:])

        PipelineRecording recording = pipeline.recording

        StageModel stage = recording.getStage('build')

        assertThat(stage.codeBlock, hadInvocation("echo", 'Building'))

    }

    @Test
    void correctStagesExist() {

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)
        pipeline.build([:])

        PipelineRecording recording = pipeline.recording

        assertEquals(2, recording.stages.size())

    }

}
