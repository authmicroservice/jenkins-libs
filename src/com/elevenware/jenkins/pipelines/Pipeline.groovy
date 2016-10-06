package com.elevenware.jenkins.pipelines

abstract class Pipeline implements Serializable {

    private static Map PIPELINES = [
            'github': GithubPipeline
    ]

    void generate() {

        new SimpleStage().create('test app', 'Integration')
        new SimpleStage().create('test app', 'QA')
        new SimpleStage().create('test app', 'Staging')
        new SimpleStage().create('test app', 'Production')

    }

    static Pipeline forType(String type) {
        Class<? extends Pipeline> pipelineClass = PIPELINES[type]
        if(!pipelineClass) {
            throw new RuntimeException("Unknown pipeline type '${type}'")
        }
        pipelineClass.newInstance()
    }

}
