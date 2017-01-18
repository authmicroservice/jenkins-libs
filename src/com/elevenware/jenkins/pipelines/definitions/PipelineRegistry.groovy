package com.elevenware.jenkins.pipelines.definitions

class PipelineRegistry implements Serializable {

    private static PipelineRegistry INSTANCE = new PipelineRegistry()

    private def registry = [:]

    private PipelineRegistry() {
        registry['githubflow'] = GithubPipelineDefinition
    }

    static PipelineRegistry getInstance() {
        INSTANCE
    }

    def create(String name) {
        if(!registry.containsValue()) {
            throw new RuntimeException("Cannot find pipeline type '${name}' in '${registry.keySet().join(' ')}'")
        }
        Class pipelineClass = registry[name]
        pipelineClass.newInstance()
    }

}
