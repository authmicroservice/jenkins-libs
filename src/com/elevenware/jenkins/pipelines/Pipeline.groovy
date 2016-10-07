package com.elevenware.jenkins.pipelines

abstract class Pipeline implements Serializable {

    private static Map TYPES = [github: GithubPipeline]

    void generate() {}

    @NonCPS
    abstract List getElements()

    static Pipeline forType(String type) {
        Class pipelineClass = TYPES[type]
        if(!pipelineClass) {
            throw new RuntimeException("No flow for '${type}' defined")
        }
        pipelineClass.newInstance()
    }

}
