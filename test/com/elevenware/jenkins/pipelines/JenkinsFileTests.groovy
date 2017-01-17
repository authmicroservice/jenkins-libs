package com.elevenware.jenkins.pipelines

import org.codehaus.groovy.control.CompilerConfiguration
import org.junit.Test

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

class JenkinsFileTests {


    @Test
    void jenkinsFileSetsAppName() {

        String jenkinsFile = new File("test/com/elevenware/jenkins/pipelines/Jenkinsfile").text
        String pipelineDef = new File("vars/githubFlowPipeline.groovy").text

        CompilerConfiguration compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = DelegatingScript.canonicalName

        def shell = new GroovyShell(getClass().classLoader, new Binding(), compilerConfiguration)

        DelegatingScript script = shell.parse(jenkinsFile)
        def delegate = new JenkinsfileDelegate("simplePipeline")
        script.setDelegate delegate

        script.run()

        PipelineContext ctx = delegate.context

        assertThat(ctx.appName, equalTo('basic-springboot-app'))
        assertThat(ctx.role, equalTo('basic-spring'))
        assertThat(ctx.platform, equalTo('java'))
        assertThat(ctx.cookbookName, equalTo('tc-j2'))

    }

}

class JenkinsfileDelegate {

    private String[] pipelines
    PipelineContext context = [:]

    JenkinsfileDelegate(String...expectedPipelines) {
        this.pipelines = expectedPipelines
    }

    Object methodMissing(String name, args) {
       if(!pipelines.contains(name)) {
           throw new RuntimeException("HAHAHA NO SUCH PIPELINE BRO $name")
       }
        Closure closure = args[0]
        closure.setDelegate(context)
        closure.setResolveStrategy(Closure.DELEGATE_ONLY)
        closure.call()
    }


}