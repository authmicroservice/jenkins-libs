package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.elements.BuildTestPublishElement
import com.elevenware.jenkins.pipelines.elements.PipelineElement

class GithubPipeline extends Pipeline {
    @Override
    List<PipelineElement> getElements() {
        [new BuildTestPublishElement()]
    }
}
