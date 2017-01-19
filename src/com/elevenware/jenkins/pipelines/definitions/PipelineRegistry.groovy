package com.elevenware.jenkins.pipelines.definitions

class PipelineRegistry implements Serializable {

    private static PipelineRegistry INSTANCE = new PipelineRegistry()

    private registry = [:]

    private PipelineRegistry() {
        registry['githubflow'] = GithubPipelineDefinition
        registry['simplePipeline'] = SimplePipelineDefinition
    }

    static PipelineRegistry getInstance() {
        INSTANCE
    }

    def create(String name) {
        if(!registry.containsKey(name)) {
            throw new RuntimeException("Cannot find pipeline type '${name}' in '${registry.keySet().join(' ')}'")
        }
        Class pipelineClass = registry[name]
        pipelineClass.newInstance()
    }

}
