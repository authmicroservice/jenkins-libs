package com.elevenware.jenkins.recording

class Invocation {

    String name
    def args

    Invocation(String name, args) {
        this.name = name
        this.args = args
    }

}
