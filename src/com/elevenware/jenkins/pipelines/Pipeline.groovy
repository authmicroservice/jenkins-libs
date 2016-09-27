package com.elevenware.jenkins.pipelines

class Pipeline implements Serializable {

    void run(object) {
        println "hello there $object"
        object.echo("OBJECT $object.delegate")
    }

}
