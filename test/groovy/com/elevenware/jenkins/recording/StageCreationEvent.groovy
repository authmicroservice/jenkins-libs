package com.elevenware.jenkins.recording

class StageCreationEvent extends DslEvent {

    String stageName

    StageCreationEvent(String name) {
        this.stageName = name
    }

}
