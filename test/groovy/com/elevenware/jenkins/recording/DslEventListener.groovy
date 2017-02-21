package com.elevenware.jenkins.recording

interface DslEventListener {

    void doReceive(DslEvent event)

}