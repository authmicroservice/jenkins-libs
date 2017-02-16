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

        assertDeploySteps(deployStage, 'integration')

        assertThat(deployStage, hadInvocation("echo", "Build number: 123"))
        assertThat(deployStage, hadInvocation("echo", "Metadata: 0123-af61a6c9"))

    }

    void assertDeploySteps(StageModel stageModel, String env) {

        ArgumentCapture capture = new ArgumentCapture(Map)

        assertThat(stageModel, hadInvocation("echo", "Deploying basic-app to $env"))

        assertThat(stageModel, hadInvocation("git", captureTo(capture)))
        assertThat(stageModel, hadInvocation("dir", 'cookbook'))
        assertThat(stageModel, hadInvocation('sh'))

        assertThat(stageModel, hadInvocation('echo', "Pinning basic-app to version <x> in environment ${env}"))

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
                cookbookDir = "cookbook"
                shortCommit = 'af61a6c9'
                chefRepo {
                    uri = 'git@github.com:ThomasCookOnline/chef-repo'
                    credentials = 'abc123'
                }
            }
        }.context

        ctx.buildNumber = 123

        pipeline.run(ctx)
        recording = pipeline.getRecording()
    }

}
