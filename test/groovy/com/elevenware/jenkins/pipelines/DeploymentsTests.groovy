package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.functions.Deployments
import com.elevenware.jenkins.recording.dsl.StageModel
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertNotNull

class DeploymentsTests {

    @Test
    void withDelegate() {

        Deployments deployments = testableScript(Deployments)

        deployments.deploy("integration", [role: 'foo'])

        def recordings = deployments.getRecording()

        assertNotNull recordings
        StageModel stage = recordings.getStage('deploy-foo-integration')

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", "Deploying foo to integration"))

        assertThat(stage.codeBlock, hadInvocation("sh"))

    }

}
