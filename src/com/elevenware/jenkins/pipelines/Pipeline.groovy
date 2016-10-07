package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.elements.PipelineElement

abstract class Pipeline implements Serializable {
//
//    private static Map PIPELINES = [
//            'github': GithubPipeline
//    ]

    void generate() {

//        getElements().each { PipelineElement element ->
//            element.generate()
//        }

    }

    abstract List getElements()

    static Pipeline forType(String type) {
//        println "LOOKING YP $type"
//        Class<? extends Pipeline> pipelineClass = PIPELINES[type]
//        if(!pipelineClass) {
//            throw new RuntimeException("Unknown pipeline type '${type}'")
//        }
//        pipelineClass.newInstance()
    }

}
