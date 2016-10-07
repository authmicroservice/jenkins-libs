package com.elevenware.jenkins.pipelines.elements

abstract class PipelineElement implements Serializable {

    @NonCPS
    void generate(steps) {
        Closure closure = {
            node {
                stage('lol') {
                    echo "HELLO"
                }
            }
        }
        steps.echo("RUNNING ${closure}")
        closure.setDelegate(steps)
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)
        closure.call()
    }

//    @NonCPS
    abstract Closure getDefinition()

}
