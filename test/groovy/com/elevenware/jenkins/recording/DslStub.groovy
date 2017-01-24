package com.elevenware.jenkins.recording

import static org.mockito.Mockito.mock

interface DslStub {

    static DslStub INSTANCE = mock(DslStub)

    abstract String libraryResource(String string)
    abstract void echo(String string)
    abstract void sh(String script)
    abstract void withMaven(Closure body)
    abstract void withMaven(Map args, Closure body)

}
