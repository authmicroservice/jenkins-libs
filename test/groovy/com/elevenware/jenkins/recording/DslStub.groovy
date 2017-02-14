package com.elevenware.jenkins.recording

import static org.mockito.Mockito.mock

interface DslStub {

    static DslStub INSTANCE = mock(DslStub)

    String libraryResource(CharSequence string)
    void echo(CharSequence string)
    void sh(CharSequence script)
    void withMaven(Closure body)
    void withMaven(Map args, Closure body)
    void git(String uri)

}
