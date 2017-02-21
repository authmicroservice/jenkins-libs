package com.elevenware.jenkins.recording.events

interface DslEventListener {

    void doReceive(DslEvent event)

}