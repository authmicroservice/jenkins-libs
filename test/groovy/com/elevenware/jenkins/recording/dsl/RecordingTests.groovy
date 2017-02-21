package com.elevenware.jenkins.recording.dsl

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.definitions.SimplePipelineDefinition
import com.elevenware.jenkins.recording.TestScripts
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertNotNull
import static org.mockito.Mockito.when

class RecordingTests {

    @Test
    void simplestPossible() {

        TestScripts scripts = testableScript(TestScripts)
        scripts.simplest()

        PipelineRecording recording = scripts.recording

        StageModel stage = recording.defaultStage()

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", "Hello, world!"))

    }

    @Test
    void multipleLines() {
        TestScripts scripts = testableScript(TestScripts)
        scripts.multiline()

        PipelineRecording recording = scripts.recording

        StageModel stage = recording.defaultStage()

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", "Hello, world!"))
        assertThat(stage.codeBlock, hadInvocation("echo", "Goodbye, world!"))
    }

    @Test
    void passedParams() {

        String paramToPass = "This is a string"
        TestScripts scripts = testableScript(TestScripts)
        scripts.passParam(paramToPass)

        PipelineRecording recording = scripts.recording

        StageModel stage = recording.defaultStage()

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", paramToPass))

    }

    @Test
    void inAStage() {

        TestScripts scripts = testableScript(TestScripts)
        scripts.inStage()

        PipelineRecording recording = scripts.recording

        StageModel stage = recording.getStage('stage1')

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", "Hello, world!"))

    }

    @Test
    void inANode() {

        TestScripts scripts = testableScript(TestScripts)
        scripts.inNode()

        PipelineRecording recording = scripts.recording

        assertNotNull recording

        StageModel stage = recording.defaultStage()

        assertThat(stage.codeBlock, hadInvocation("echo", "Hello, world!"))

    }

    @Test
    void stageInsideNode() {
        TestScripts scripts = testableScript(TestScripts)
        scripts.stageInNode()

        PipelineRecording recording = scripts.recording

        assertNotNull recording

        StageModel stage = recording.getStage('stage1')

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", "Hello, world!"))
    }

    @Test
    void mockResponses() {

        String string = 'Hello, World!'

        TestScripts scripts = testableScript(TestScripts)
        when(scripts.stub.libraryResource('some_path.txt')).thenReturn(string)

        scripts.mockResponse()


        PipelineRecording recording = scripts.recording

        StageModel stage = recording.defaultStage()
        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", string))

    }

    @Test
    void recordInPipeline() {

        String appName = 'foo'
        PipelineContext ctx = new PipelineContext()
        ctx.appName = appName

        SimplePipelineDefinition pipeline = testableScript(SimplePipelineDefinition)

        pipeline.run(ctx)

        PipelineRecording recordings = pipeline.recording

        assertNotNull recordings
        StageModel stage = recordings.getStage('build foo')

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", "Running build stage for foo"))

    }

}
