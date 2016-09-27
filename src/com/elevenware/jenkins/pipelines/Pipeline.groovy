package com.elevenware.jenkins.pipelines

//class Pipeline implements Serializable {

    def environments = ['integration', 'qa', 'staging', 'production']

    void generate() {
        environments.each { env ->
            new SimpleStage().create('test app', env)
        }
    }


//}
