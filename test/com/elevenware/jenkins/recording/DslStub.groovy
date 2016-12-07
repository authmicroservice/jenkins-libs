package com.elevenware.jenkins.recording

interface DslStub {

    String libraryResource(String string)
    void echo(String string)
    void sh(String script)


}
