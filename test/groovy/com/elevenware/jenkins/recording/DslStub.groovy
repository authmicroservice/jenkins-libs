package com.elevenware.jenkins.recording

import static org.mockito.Mockito.mock

interface DslStub {

    static DslStub INSTANCE = mock(DslStub)

    String libraryResource(String string)
    void echo(String string)
    void sh(String script)
    void withMaven(Closure body)
    void withMaven(Map args, Closure body)

}
