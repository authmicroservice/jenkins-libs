package com.elevenware.jenkins.pipelines

abstract class Pipeline {

    private static Map TYPES = [
            'github': GithubPipeline
    ]

    static Pipeline forType(String type) {
        Class pipelineClass = TYPES[type]
        if(!pipelineClass) {
            throw new RuntimeException("No defined flow for $type")
        }
        pipelineClass.newInstance()
    }

}
