package com.elevenware.jenkins.recording

import com.elevenware.jenkins.pipelines.SimplePipelineDefinition
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testable
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertNotNull
import static org.mockito.Mockito.when

class RecordingTests {

    @Test
    void simplestPossible() {

        TestScripts scripts = testable(TestScripts)
        scripts.simplest()

        PipelineRecording recording = scripts.recording

        StageModel stage = recording.defaultStage()

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", "Hello, world!"))

    }

    @Test
    void multipleLines() {
        TestScripts scripts = testable(TestScripts)
        scripts.multiline()

        PipelineRecording recording = scripts.recording

        StageModel stage = recording.defaultStage()

        assertNotNull stage

//        assertThat(stage.codeBlock, hadInvocation("echo", "Hello, world!"))
        assertThat(stage.codeBlock, hadInvocation("echo", "Goodbye, world!"))
    }

    @Test
    void passedParams() {

        String paramToPass = "This is a string"
        TestScripts scripts = testable(TestScripts)
        scripts.passParam(paramToPass)

        PipelineRecording recording = scripts.recording

        StageModel stage = recording.defaultStage()

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", paramToPass))

    }

    @Test
    void inAStage() {

        TestScripts scripts = testable(TestScripts)
        scripts.inStage()

        PipelineRecording recording = scripts.recording

        StageModel stage = recording.getStage('stage1')

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", "Hello, world!"))

    }

    @Test
    void inANode() {

        TestScripts scripts = testable(TestScripts)
        scripts.inNode()

        PipelineRecording recording = scripts.recording

        assertNotNull recording

        NodeModel node = recording.getNode(0)

        assertNotNull node

        assertThat(node.codeBlock, hadInvocation("echo", "Hello, world!"))

    }

    @Test
    void stageInsideNode() {
        TestScripts scripts = testable(TestScripts)
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

        TestScripts scripts = testable(TestScripts)
        when(scripts.stub.libraryResource('some_path.txt')).thenReturn(string)

        scripts.mockResponse()


        PipelineRecording recording = scripts.recording

        StageModel stage = recording.defaultStage()
        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", string))

    }

    @Test
    void recordInPipeline() {

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)

        pipeline.build([:])

        PipelineRecording recordings = pipeline.recording

        assertNotNull recordings
        StageModel stage = recordings.getStage('build')

        assertNotNull stage

        assertThat(stage.codeBlock, hadInvocation("echo", "Building"))

    }

}
