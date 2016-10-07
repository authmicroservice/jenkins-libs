package com.elevenware.jenkins.pipelines

abstract class Pipeline {

    private static Map TYPES = [
            'github': GithubPipeline
    ]

    static Pipeline forType(String type) {

    }

}
