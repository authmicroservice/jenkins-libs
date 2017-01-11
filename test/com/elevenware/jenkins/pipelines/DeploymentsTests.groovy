package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.functions.Deployments
import com.elevenware.jenkins.recording.DslDelegate
import com.elevenware.jenkins.recording.CodeBlock
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testable
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertNotNull

class DeploymentsTests {

    @Test
    void withDelegate() {

        Deployments deployments = testable(Deployments) //new Deployments()

        deployments.deploy("integration", [role: 'foo'])

        def recordings = deployments.getRecordings()

        assertNotNull recordings
        CodeBlock stage = recordings.stages['stage_deploy-foo-integration']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Deploying foo to integration"))

        assertThat(stage, hadInvocation("sh"))

    }

}
