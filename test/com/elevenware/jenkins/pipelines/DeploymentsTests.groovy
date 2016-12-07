package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.functions.Deployments
import com.elevenware.jenkins.recording.DslDelegate
import com.elevenware.jenkins.recording.DslStub
import com.elevenware.jenkins.recording.StageModel
import org.junit.Test
import org.mockito.Mockito

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertNotNull
import static org.mockito.Mockito.*

class DeploymentsTests {

    @Test
    void withDelegate() {

        Deployments deployments = new Deployments()
        deployments.metaClass {
            mixin DslDelegate
        }

        deployments.deploy("integration", [role: 'foo'])

        def recordings = deployments.getRecordings()

        assertNotNull recordings
        StageModel stage = recordings.stages['deploy-foo-integration']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Deploying foo to integration"))

        assertThat(stage, hadInvocation("sh"))

    }

}
