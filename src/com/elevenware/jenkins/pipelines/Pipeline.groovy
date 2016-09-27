package com.elevenware.jenkins.pipelines

class Pipeline implements Serializable {

//    Pipeline(Closure config) {
//        config.setDelegate(this)
//        config.setResolveStrategy(Closure.DELEGATE_FIRST)
//        config.call()
//    }
//
//    String name

    def environments = ['integration', 'qa', 'staging', 'production']

    void run(object) {
        environments.each { env ->
            new SimpleStage().create('test app', env)
        }
    }

//    void name(String name) {
//        this.name = name
//    }

}
