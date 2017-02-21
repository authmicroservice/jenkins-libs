package com.elevenware.jenkins.demo

import com.elevenware.jenkins.pipelines.PipelineContext
import com.elevenware.jenkins.pipelines.definitions.SimplePipelineDefinition
import com.elevenware.jenkins.recording.dsl.PipelineRecording
import com.elevenware.jenkins.recording.dsl.StageModel
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.matchers.DslMatchers.isString
import static com.elevenware.jenkins.recording.DslTestHelper.testableScript
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertEquals

class SimplePipelineTests {

    @Test
    void correctStagesExist() {

        String appName = 'Foo Application'
        PipelineContext ctx = new PipelineContext()
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
        PipelineContext ctx = new PipelineContext()
        ctx.appName = appName

        SimplePipelineDefinition pipeline = testableScript(SimplePipelineDefinition)

        pipeline.run(ctx)

        PipelineRecording recording = pipeline.recording

        StageModel node = recording.getStage('build Foo Application')

        assertThat(node.codeBlock, hadInvocation("echo", "Running build stage for $appName"))
        assertThat(node.codeBlock, hadInvocation("withMaven"))

    }

    @Test
    void deployStageActsAsExpected() {

        String appName = 'Foo Application'
        PipelineContext ctx = new PipelineContext()
        ctx.appName = appName

        SimplePipelineDefinition pipeline = testableScript(SimplePipelineDefinition)

        pipeline.run(ctx)

        PipelineRecording recording = pipeline.recording

        StageModel deploy = recording.getStage('deploy Foo Application')

        assertThat(deploy.codeBlock, hadInvocation("echo", "Running deploy stage for $appName"))

    }

}
