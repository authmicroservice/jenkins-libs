package com.elevenware.jenkins.recording

import org.codehaus.groovy.control.CompilerConfiguration

class DslTestHelper {

    static def testableScript(Class scriptClass) {
        def script = scriptClass.newInstance()
        script.metaClass {
            mixin DslDelegate
        }
        return script
    }

    static def testableSnippet(Map args = [:], Closure closure) {
        DslDelegate delegate = new DslDelegate()
        closure.setDelegate(delegate)
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)
        closure.call()
        delegate.recording.defaultStage().codeBlock
    }

    static def testableJenkinsfile(String pathToJenkinsFile) {
        String jenkinsFile = new File("test/resources/$pathToJenkinsFile").text

        CompilerConfiguration compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = DelegatingScript.canonicalName

        def shell = new GroovyShell(getClass().classLoader, new Binding(), compilerConfiguration)

        DelegatingScript script = shell.parse(jenkinsFile)
        def delegate = new JenkinsfileDelegate()
        script.setDelegate delegate
        script.run()
        delegate
    }

}
