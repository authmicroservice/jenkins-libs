package com.elevenware.jenkins.scripts

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.definitions.ChefSteps
import com.elevenware.jenkins.pipelines.definitions.ShellSnippets
import com.elevenware.jenkins.recording.DslStub
import com.elevenware.jenkins.recording.PipelineRecording
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat
import static org.mockito.Mockito.when

class ChefStepsTests {

    @Test
    void environmentPinDoesNotHappenIfEnvironmentDoesntExist() {

       ChefSteps chefSteps = testableScript(ChefSteps)

        PipelineRecording recording = chefSteps.recording

        PipelineContext context = new PipelineContext()
        context.platformImplementation = new Object() { String getVersion() {
            '1.0.1-0894-1abbbbae'
        }}

        when(DslStub.INSTANCE.sh(ShellSnippets.KNIFE_CHECK_ENV.format('integration'))).thenReturn(-1)

        context.appName = 'foo-app'
        context.appVersion = '1.0.1-0894-1abbbbae'
        context.cookbookDir = 'site-cookbooks/tc-canary'

        chefSteps.environmentPin(context, 'integration')

        assertThat(recording.defaultStage(), hadInvocation("echo", "Pinning foo-app to version foo-app@1.0.1-0894-1abbbbae in environment integration"))
        assertThat(recording.defaultStage(), not((hadInvocation("echo", "now let's pin"))))

    }

    @Test
    void varargs() {

        def arg = 'integration'

        def cmd = ShellSnippets.KNIFE_CHECK_ENV.format(arg)

        assertThat(cmd, equalTo("bundle exec knife integration"))

    }

}
