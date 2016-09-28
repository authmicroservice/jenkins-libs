package com.elevenware.jenkins.pipelines

//class Pipeline implements Serializable {



    void generate() {

        def environments = ['integration', 'qa', 'staging', 'production']

        environments.each { env ->
            new SimpleStage().create('test app', 'integration')
            new SimpleStage().create('test app', 'qa')
            new SimpleStage().create('test app', 'staging')
            new SimpleStage().create('test app', 'production')
        }
    }


//}
