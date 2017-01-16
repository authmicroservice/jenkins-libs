package com.elevenware.jenkins.demo

import com.elevenware.jenkins.recording.PipelineRecording
import com.elevenware.jenkins.recording.StageModel
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.matchers.DslMatchers.isString
import static com.elevenware.jenkins.recording.DslTestHelper.testable
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.*

class SimplePipelineTests {

    @Test
    void correctStagesExist() {

        String appName = 'Foo Application'

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)
        pipeline.build([appName: appName])

        PipelineRecording recording = pipeline.recording

        assertEquals(2, recording.stages.size())

        Iterator iter = recording.stages.entrySet().iterator()

        assertThat(iter.next().value.name, isString("build Foo Application"))
        assertThat(iter.next().value.name, isString("deploy $appName"))

    }

    @Test
    void runSimplePipeline() {

        String appName = 'Foo Application'

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)

        pipeline.build([appName: appName])

        PipelineRecording recording = pipeline.recording

        StageModel stage = recording.getStage("build $appName")

        assertThat(stage.codeBlock, hadInvocation("echo", "Building $appName"))

    }

}
