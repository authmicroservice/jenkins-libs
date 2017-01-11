package com.elevenware.jenkins.pipelines
import com.elevenware.jenkins.pipelines.elements.BuildTestPublishElement
import com.elevenware.jenkins.pipelines.elements.PipelineElement
import com.elevenware.jenkins.pipelines.elements.StopElement
import groovy.json.JsonBuilder
import org.junit.Rule
import org.junit.Test
import org.jvnet.hudson.test.JenkinsRule

import static org.mockito.Mockito.mock

class BuildTestPublishElementTests {

    @Rule public JenkinsRule jenkins = new JenkinsRule()

//    @Test
    void something() {

        JsonBuilder builder = new JsonBuilder()

        def platform = mock(Platform)
        PipelineElement element = new BuildTestPublishElement(StopElement.instance)

        element.generate(platform)

    }

}
