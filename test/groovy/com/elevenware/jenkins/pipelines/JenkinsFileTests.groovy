package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.definitions.SimplePipelineDefinition
import com.elevenware.jenkins.recording.DslTestHelper
import com.elevenware.jenkins.recording.JenkinsfileDelegate
import org.junit.Test

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.instanceOf
import static org.junit.Assert.assertThat

class JenkinsFileTests {

    @Test
    void jenkinsFileConfiguresCorrectly() {

        JenkinsfileDelegate delegate = DslTestHelper.testableJenkinsfile("Jenkinsfile")

        PipelineContext ctx = delegate.context

        assertThat(delegate.pipelineDefinition, instanceOf(SimplePipelineDefinition))
        assertThat(ctx.appName, equalTo('basic-app'))
        assertThat(ctx.role, equalTo('basic'))
        assertThat(ctx.platform, equalTo('java'))
        assertThat(ctx.cookbookName, equalTo('tc-basic'))

    }

    @Test
    void jenkinsFileAsInlineClosure() {

        JenkinsfileDelegate delegate = DslTestHelper.testableJenkinsfileClosure {
            runPipeline('simplePipeline') {
                appName = 'basic-app'
                role = 'basic'
                platform = 'java'
                cookbookName = 'tc-basic'
            }
        }

        PipelineContext ctx = delegate.context

        assertThat(delegate.pipelineDefinition, instanceOf(SimplePipelineDefinition))
        assertThat(ctx.appName, equalTo('basic-app'))
        assertThat(ctx.role, equalTo('basic'))
        assertThat(ctx.platform, equalTo('java'))
        assertThat(ctx.cookbookName, equalTo('tc-basic'))

    }

}

