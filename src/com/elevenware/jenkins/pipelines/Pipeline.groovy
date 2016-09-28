package com.elevenware.jenkins.pipelines

class Pipeline implements Serializable {

    void generate() {

        def environments = ['integration', 'qa', 'staging', 'production']

        for(String env: environments) {
            new SimpleStage().create('test app', env)
        }
    }

}
