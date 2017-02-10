package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.definitions.GithubPipelineDefinition
import com.elevenware.jenkins.recording.CodeBlock
import com.elevenware.jenkins.recording.PipelineRecording
import com.elevenware.jenkins.recording.StageModel
import org.junit.Before
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testableJenkinsfileClosure
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertNotNull

class GithubPipelineTests {

    PipelineContext ctx
    GithubPipelineDefinition pipeline
    PipelineRecording recording

    @Test
    void buildStageW() {

        StageModel stage = recording.getStage('Build basic-app')

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "building"))

    }

    @Before
    void setup() {

        pipeline = testableScript(GithubPipelineDefinition)
        ctx = testableJenkinsfileClosure(pipeline.recording) {
            runPipeline('githubflow') {
                appName = 'basic-app'
                role = 'basic'
                platform = 'java'
                cookbookName = 'tc-basic'
            }
        }.context


        pipeline.run(ctx)
        recording = pipeline.getRecording()
    }

}
