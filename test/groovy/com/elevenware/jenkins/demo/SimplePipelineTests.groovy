package com.elevenware.jenkins.demo

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.definitions.SimplePipelineDefinition
import com.elevenware.jenkins.recording.PipelineRecording
import com.elevenware.jenkins.recording.StageModel
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.matchers.DslMatchers.isString
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.*

class SimplePipelineTests {

    @Test
    void correctStagesExist() {

        String appName = 'Foo Application'
        PipelineContext ctx = new PipelineContext("")
        ctx.appName = appName

        SimplePipelineDefinition pipeline = testableScript(SimplePipelineDefinition)
        pipeline.run(ctx)

        PipelineRecording recording = pipeline.recording

        assertEquals(2, recording.stages.size())

        Iterator iter = recording.stages.entrySet().iterator()

        assertThat(iter.next().value.name, isString("build Foo Application"))
        assertThat(iter.next().value.name, isString("deploy $appName"))

    }

    @Test
    void buildStageActsAsExpected() {

        String appName = 'Foo Application'
        PipelineContext ctx = new PipelineContext("")
        ctx.appName = appName

        SimplePipelineDefinition pipeline = testableScript(SimplePipelineDefinition)

        pipeline.run(ctx)

        PipelineRecording recording = pipeline.recording

        StageModel buildStage = recording.getStage("build $appName")

        assertThat(buildStage.codeBlock, hadInvocation("echo", "Running build stage for $appName"))

    }

    @Test
    void deployStageActsAsExpected() {

        String appName = 'Foo Application'
        PipelineContext ctx = new PipelineContext("")
        ctx.appName = appName

        SimplePipelineDefinition pipeline = testableScript(SimplePipelineDefinition)

        pipeline.run(ctx)

        PipelineRecording recording = pipeline.recording

        StageModel deployStage = recording.getStage("deploy $appName")

        assertThat(deployStage.codeBlock, hadInvocation("echo", "Running deploy stage for $appName"))

    }

}
