package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.matchers.ArgumentCapture
import com.elevenware.jenkins.pipelines.definitions.GithubPipelineDefinition
import com.elevenware.jenkins.recording.PipelineRecording
import com.elevenware.jenkins.recording.StageModel
import org.junit.Before
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.*
import static com.elevenware.jenkins.recording.DslTestHelper.testableJenkinsfileClosure
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.IsEqual.equalTo
import static org.mockito.Matchers.isA

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

    @Test
    void buildStageWorksAsExpected() {

        StageModel buildStage = recording.getStage("Build basic-app")

        assertThat(buildStage, hadInvocation("echo", "building basic-app on simple platform"))

    }

    @Test
    void deployToIntegration() {

        StageModel deployStage = recording.getStage("Deploy basic-app to integration")

        assertThat(deployStage, hadInvocation("echo", "Deploying basic-app to integration"))
        assertThat(deployStage, hadInvocation("git", isA(Map)))

        ArgumentCapture capture = new ArgumentCapture(Map)

        assertThat(deployStage, hadInvocation("git", captureTo(capture)))

        Map args = capture.value()

        assertThat(args.get("url"), equalTo("git@github.com:ThomasCookOnline/chef-repo"))
        assertThat(args.get("credentialsId"), equalTo("abc123"))

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
                chefRepo {
                    uri = 'git@github.com:ThomasCookOnline/chef-repo'
                    credentials = 'abc123'
                }
            }
        }.context


        pipeline.run(ctx)
        recording = pipeline.getRecording()
    }

}
