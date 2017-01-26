package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.definitions.GithubPipelineDefinition
import com.elevenware.jenkins.recording.CodeBlock
import com.elevenware.jenkins.recording.PipelineRecording
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertNotNull

class GithubPipelineTests {




    @Test
    void buildStage() {

        GithubPipelineDefinition pipeline = testableScript(GithubPipelineDefinition)

        PipelineContext ctx = new PipelineContext()

        pipeline.run(ctx)

        PipelineRecording recordings = pipeline.getRecording()

        CodeBlock stage = recordings.defaultStage().codeBlock

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Hello"))

    }

}
