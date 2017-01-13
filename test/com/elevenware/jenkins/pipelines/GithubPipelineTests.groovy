package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.definitions.GithubPipelineDefinition
import com.elevenware.jenkins.recording.CodeBlock
import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static com.elevenware.jenkins.recording.DslTestHelper.testable
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNotNull

class GithubPipelineTests {

//    @Test
    void buildTestPublish() {

        GithubPipelineDefinition pipeline = testable(GithubPipelineDefinition)

        pipeline.buildAndPublishArtifact([:])

        def recordings = pipeline.getRecording()

        assertNotNull recordings
        CodeBlock stage = recordings.getStage('buildTestPublish').codeBlock

        assertNotNull stage

        assertThat(stage, hadInvocation("echo", "Hello, world!"))

    }

}
