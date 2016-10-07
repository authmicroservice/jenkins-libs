package com.elevenware.jenkins.pipelines.elements

abstract class PipelineElement implements Serializable {

    @NonCPS
    void generate(steps) {
        Closure closure = getDefinition()
        closure.setDelegate(steps)
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.call()
    }

    @NonCPS
    abstract Closure getDefinition()

}
