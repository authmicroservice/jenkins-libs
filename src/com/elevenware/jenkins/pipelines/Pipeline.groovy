package com.elevenware.jenkins.pipelines

class Pipeline {

    private static Map TYPES = [
            'github': GithubPipeline
    ]

    static Pipeline forType(String type) {

    }

}
