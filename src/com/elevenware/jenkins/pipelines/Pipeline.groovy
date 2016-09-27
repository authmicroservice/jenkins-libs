package com.elevenware.jenkins.pipelines

//class Pipeline implements Serializable {



    void generate() {

        def environments = ['integration', 'qa', 'staging', 'production']
        
        environments.each { env ->
            new SimpleStage().create('test app', env)
        }
    }


//}
