package com.elevenware.jenkins.recording

import static org.mockito.Mockito.mock
/**
 * DslStub
 *
 * This interface is the source of all the mocked DSL commands available
 * It is also used for stubbing responses using Mockito.
 *
 * Any methods which take a Closure as an argument will have the DslDelegate wired into the closure
 * automatically and then the closure executed so that the code within the closure behaves
 * as expected.
 *
 * {bold}Note: when adding a method which takes a java.lang.String, favour java.lang.CharSequence as
 *             this works around weirdness with Groovy's GString{bold}
 */
interface DslStub {

    static DslStub INSTANCE = mock(DslStub)

    String libraryResource(CharSequence string)
    void echo(CharSequence string)
    def sh(CharSequence script)
    void withMaven(Closure body)
    void withMaven(Map args, Closure body)
    void git(String uri)
    void git(Map config)
    void dir(CharSequence dir, Closure body)

}
