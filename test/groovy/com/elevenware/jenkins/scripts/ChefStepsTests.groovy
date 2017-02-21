package com.elevenware.jenkins.scripts

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.definitions.ChefSteps
import com.elevenware.jenkins.pipelines.definitions.ShellSnippets
import com.elevenware.jenkins.recording.dsl.DslStub
import com.elevenware.jenkins.recording.FailedStepException
import com.elevenware.jenkins.recording.dsl.PipelineRecording
import org.junit.Before
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.CommonMocks.mockCurrentAppSpec
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.Matchers.not
import static org.junit.Assert.assertThat
import static org.mockito.Mockito.when

class ChefStepsTests {

    @Test( expected = FailedStepException)
    void environmentPinDoesNotHappenIfEnvironmentDoesntExist() {

       ChefSteps chefSteps = testableScript(ChefSteps)

        PipelineRecording recording = chefSteps.recording

        PipelineContext context = new PipelineContext()
        context.platformImplementation = new Object() { String getVersion() {
            '1.0.1-0894-1abbbbae'
        }}

        when(DslStub.INSTANCE.sh(ShellSnippets.KNIFE_CHECK_ENV.format('integration'))).thenThrow(new FailedStepException("cannot proceed"))

        context.appName = 'foo-app'
        context.appVersion = '1.0.1-0894-1abbbbae'
        context.cookbookDir = 'site-cookbooks/tc-canary'

        chefSteps.environmentPin(context, 'integration')

        assertThat(recording.defaultStage(), hadInvocation("echo", "Pinning foo-app to version foo-app@1.0.1-0894-1abbbbae in environment integration"))
        assertThat(recording.defaultStage(), not(hadInvocation("echo", "Now let's pin")))

    }

    @Test
    void environmentPinDoesWorks() {

        ChefSteps chefSteps = testableScript(ChefSteps)

        PipelineRecording recording = chefSteps.recording

        PipelineContext context = new PipelineContext()
        context.platformImplementation = new Object() { String getVersion() {
            '1.0.1-0894-1abbbbae'
        }}

        context.appName = 'foo-app'
        context.appVersion = '1.0.1-0894-1abbbbae'
        context.cookbookDir = 'site-cookbooks/tc-canary'

        chefSteps.environmentPin(context, 'integration')

        assertThat(recording.defaultStage(), hadInvocation("echo", "Pinning foo-app to version foo-app@1.0.1-0894-1abbbbae in environment integration"))
        assertThat(recording.defaultStage(), hadInvocation("sh", ShellSnippets.KNIFE_CHECK_ENV.format('integration')))

    }

    @Test
    void currentVersionWorks() {

       ChefSteps chefSteps = testableScript(ChefSteps)

       PipelineContext ctx = new PipelineContext()
       ctx.appName = 'foo-app'
       ctx.cookbookName = 'tc-foo'

       def currentVersion = chefSteps.currentVersion(ctx, 'integration')

       assertThat(currentVersion, equalTo("= 1.2.3"))

    }

    @Before
    void setup() {

        mockCurrentAppSpec('tc-foo', '= 1.2.3')

    }


}
