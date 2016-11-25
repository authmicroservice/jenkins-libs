package com.elevenware.jenkins.pipelines
import com.elevenware.jenkins.pipelines.elements.BuildTestPublishElement
import com.elevenware.jenkins.pipelines.elements.PipelineElement
import groovy.json.JsonBuilder
import org.junit.Rule
import org.junit.Test
import org.jvnet.hudson.test.JenkinsRule

import static org.mockito.Mockito.mock

class BuildTestPublishElementTests {

    @Rule JenkinsRule jenkins = new JenkinsRule()

    @Test
    void something() {

        JsonBuilder builder = new JsonBuilder()

        def platform = mock(Platform)
        PipelineElement element = new BuildTestPublishElement()

        element.generate(platform)

    }

}
