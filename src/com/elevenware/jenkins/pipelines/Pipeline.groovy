package com.elevenware.jenkins.pipelines

class Pipeline {

    private static Map TYPES = new HashMap()

    static Pipeline forType(String type) {

    }

    private static void ensurePopulated() {
        if(!TYPES.isEmpty()) { return }
        TYPES.put('github', GithubPipeline.class)
    }

}
