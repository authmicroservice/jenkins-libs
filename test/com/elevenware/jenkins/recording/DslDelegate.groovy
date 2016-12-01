package com.elevenware.jenkins.recording

class DslDelegate {

    private Map recordings = [:]

    def node(Closure closure) {
        recordings['node'] = closure
        NodeDelegate nodeDelegate = new NodeDelegate()
    }

    def getRecordings() {
        recordings
    }

    def methodMissing(String name, args) {
        println "Called for ${name} with ${args}"
    }

}
