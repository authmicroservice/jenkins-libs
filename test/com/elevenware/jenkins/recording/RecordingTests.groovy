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

        def recordings = scripts.getRecordings()

        assertNotNull recordings
        CodeBlock stage = recordings.stages['defaultModel']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Hello, world!"))

    }

    @Test
    void passedParams() {

        String paramToPass = "This is a string"
        TestScripts scripts = testable(TestScripts)
        scripts.passParam(paramToPass)

        def recordings = scripts.getRecordings()

        assertNotNull recordings
        CodeBlock stage = recordings.stages['defaultModel']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", paramToPass))

    }

    @Test
    void inAStage() {

        TestScripts scripts = testable(TestScripts)
        scripts.inStage()

        def recordings = scripts.getRecordings()

        assertNotNull recordings
        CodeBlock stage = recordings.stages['stage_stage1']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Hello, world!"))

    } // 084488 12135 Kate Taylor

    @Test
    void inANode() {

        TestScripts scripts = testable(TestScripts)
        scripts.inNode()

        def recordings = scripts.getRecordings()

        assertNotNull recordings

        CodeBlock stage = recordings.stages['node_1']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Hello, world!"))

    }

    @Test
    void stageInsideNode() {
        TestScripts scripts = testable(TestScripts)
        scripts.stageInNode()

        def recordings = scripts.getRecordings()

        assertNotNull recordings

        CodeBlock stage = recordings.stages['stage_stage1']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Hello, world!"))
    }

    @Test
    void mockResponses() {

        String string = 'Hello, World!'

        TestScripts scripts = testable(TestScripts)
        when(scripts.stub.libraryResource('some_path.txt')).thenReturn(string)

        scripts.mockResponse()


        def recordings = scripts.getRecordings()

        assertNotNull recordings

        CodeBlock stage = recordings.stages['defaultModel']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", string))

    }

    @Test
    void recordInPipeline() {

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)

        pipeline.build([:])

        def recordings = pipeline.getRecordings()

        assertNotNull recordings
        CodeBlock stage = recordings.stages['stage_build']

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Building"))

    }

}
