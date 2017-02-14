package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.definitions.GithubPipelineDefinition
import com.elevenware.jenkins.recording.PipelineRecording
import org.junit.Before
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.*
import static com.elevenware.jenkins.recording.DslTestHelper.testableJenkinsfileClosure
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.MatcherAssert.assertThat

class GithubPipelineTests {

    PipelineContext ctx
    GithubPipelineDefinition pipeline
    PipelineRecording recording

    @Test
    void allStagesPresent() {

        assertThat(recording, hasStage('Build basic-app', inFirstPosition()))
        assertThat(recording, hasStage('Deploy basic-app to integration', inPosition(1)))
        assertThat(recording, hasStage('Deploy basic-app to QA', inPosition(2)))
        assertThat(recording, hasStage('Deploy basic-app to staging', inPosition(3)))
        assertThat(recording, hasStage('Deploy basic-app to production', inPosition(4)))

    }

    @Before
    void setup() {

        pipeline = testableScript(GithubPipelineDefinition)
        ctx = testableJenkinsfileClosure(pipeline.recording) {
            runPipeline('githubflow') {
                appName = 'basic-app'
                role = 'basic'
                platform = 'simple'
                cookbookName = 'tc-basic'
            }
        }.context


        pipeline.run(ctx)
        recording = pipeline.getRecording()
    }

}
