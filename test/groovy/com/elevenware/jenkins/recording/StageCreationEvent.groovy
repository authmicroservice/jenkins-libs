package com.elevenware.jenkins.recording

import com.elevenware.jenkins.recording.events.DslEvent

class StageCreationEvent extends DslEvent {

    String stageName

    StageCreationEvent(String name) {
        this.stageName = name
    }

}
