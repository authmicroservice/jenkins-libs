package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.elements.PipelineElement

abstract class Pipeline implements Serializable {

    private static Map TYPES = [github: GithubPipeline]

    protected Platform platform

    Pipeline(Platform platform) {
        this.platform = platform
    }

    @NonCPS
    void generate() {

        getElements().each { PipelineElement element ->
            element.generate(this.platform)
        }

    }

    @NonCPS
    abstract List getElements()

    static Class<Pipeline> forType(String type) {
        Class pipelineClass = TYPES[type]
        if(!pipelineClass) {
            throw new RuntimeException("No flow for '${type}' defined")
        }
        pipelineClass
    }

}
