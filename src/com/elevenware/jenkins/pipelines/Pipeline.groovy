package com.elevenware.jenkins.pipelines

class Pipeline {

    private static Map<String, Class<? extends Pipeline>> TYPES = [
            github: GithubPipeline
    ]

    static Pipeline forType(String type) {

    }

}
