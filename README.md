# jenkins-libs Documentation

This project is an alternative approach to the jenkins-jobdsl project for providing configuration-as-code for Jenkins pipelines.
 
 ## Preamble
 
 The motivation for this project is a desire to remove our dependence on an old, forked verion of the jobdsl plugin, which was holding us back from upgrading, well, anything really. 
 
 This project leverages the native Jenkins 2 pipeline DSL, which is likely to be the most well-supported approach since it is core Jenkins. 
 
 ## Concepts
 
 ### Job DSL vs Pipeline DSL
 
 Forget what you know about the Jenkins job DSL. This isn't it. Jenkins Job DSL is code that is run in a separate job to generate static configuration for other jobs. The pipeline DSL is not that, it is code that is run *as part of your pipeline*, and it's run every time the pipeline is invoked. This is important as it has an impact on testability. In fact, the biggest challenge from moving from Job DSL to the pipeline DSL is in writing testable code. Job DSL code was easy, we used a class from the DSL plugin to generate the static XML and compared it to a known control XML file. That isn't an option here, as the output of running pipeline DSL code is a pipeline run, not some XML.
 
 To sum up: Jenkins Job DSL is just an alternative way to generate config for some Jenkins jobs, the pipeline DSL is actual code which is executed as part of a pipeline itself.
 
### Repository config

Remember .jenkins.yml? It's gone. Replaced by a Jenkinsfile, which is Groovy code. It's possible to define an entire pipeline inside this file. However, this isn't scalable, and it isn't what we're aiming to do. Instead, this file will be used in conjunction with shared libraries.

### Shared libraries

Shared libraries are repositories of Groovy code of which Jenkins is aware, which provide functionality available to support Jenkinsfiles. This project *is* a shared library. It aims to provide reusable, congigurable pipeline definitions which will be configured by Jenkinsfiles. 

A word on coding style; jenkins-jobdsl had a heady mix of procedural and object-oriented code which generated pipeline DSL code in a number of ways. This was fairly complex and contained a lot of abstractions. Early attempts to mimic this level of abstraction in pipeline DSL failed, because of the underlying paradigm of the pipeline DSL. In short, pipeline DSL code is pumped through a CPS parser, which means it must be written in a particular style, or code must be marked with a @NonCPS annotation to avoid this. This quickly got out of hand and I found that most of the code was being polluted with this annotation. I've dropped this style in favour of having simpler pipelines defined in scripts, and emphasising ways of making these testable. So a large chunk of this libary is aimed at mocking out the underlying mechanism for running these scripts.

### Github Organisations

Another great departure from our old way of working is support for Jenkins to observe entire GitHub organisations. The practical upshot of this is that we don't need to explicitly add new repositories to any configuration like projects.txt. Jenkins simply receives notification of a new repository being added to the organisation, and if it has a Jenkinsfile in it, executes it.

## Simple pipeline tutorial

Let's build a simple pipeline, with tests. Our pipeline will consist of two stages, a build and a deploy. Nothing too complex. The entire pipeline and test suite are in the package 'com.elevenware.jenkins.demo' in the test folder.

First, we want a test to ensure that both of these stages exist in the correct order. Write a test that looks like this.

```groovy

import static com.elevenware.jenkins.recording.DslTestHelper.testable
import com.elevenware.jenkins.recording.PipelineRecording

@Test
    void correctStagesExist() {

        String appName = 'Foo Application'

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)
        pipeline.build([appName: appName])

        PipelineRecording recording = pipeline.recording

        assertEquals(2, recording.stages.size())

        Iterator iter = recording.stages.entrySet().iterator()

        assertThat(iter.next().value.name, isString("build $appName"))
        assertThat(iter.next().value.name, isString("deploy $appName"))

    }
```

The *testable* method is a static import from the test framework provided in this library. It wraps a DSL script in some plumbing to allow us to mock out the DSL and perform assertions on it.

SimplePipelineDefintion is a Groovy *script* (not *class*) which defines our pipeline. The use of scripts rather than classes is a coding style imposed by the DSL plugins which apply a paradigm known as CPS (continuation passing style) to DSL code. It *is* possible to avoid, but some Groovy code is not legal in the CPS style, and this would need annotating as such, which pollutes the code to a huge degree and makes it unreadable. Simplicity being key, we ran with Groovy scripts.

PipelineRecording is a framework class which represents the interactions between our DSL code and our mocked DSL.

This test, of course, will not pass, as we have not yet written the pipeline definition. It's TDD, after all. Let's add the simple pipeline.

Quick note: we've used assertThat/isString, a utility provided by the framework, rather than mere isEqual as a workaround for a Groovy/Junit quirk: isEqual will fail if you try to compare a Java String to a Groovy GString, which is what we end up with if we use interpolation.

```groovy
def build(Map config) {
    stage('build') {
    }
    stage('deploy') {
    }
}
```

This is incredibly simple. The stages do not do anything, they merely echo what they *would* do. Fine for now. Our test should now pass.

Obviously, this is so simple it isn't worth even bothering to test. So let's add a little logic so that it *is*.

Stage names - build and deploy in this example - are actually displayed in the Jenkins UI. Since this pipeline, as with all of ours, are intended to be reusable across applications, let's [make it so](https://s-media-cache-ak0.pinimg.com/736x/dc/df/5b/dcdf5b42acb95fa3551faa23a2f1e9e4.jpg).

Change the test to look like this

```groovy
@Test
    void correctStagesExist() {

        String appName = 'Foo Application'

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)
        pipeline.build([appName: appName])

        PipelineRecording recording = pipeline.recording

        assertEquals(2, recording.stages.size())

        Iterator iter = recording.stages.entrySet().iterator()

        assertThat(iter.next().value.name, equalTo("build Foo Application"))
        assertThat(iter.next().value.name, equalTo("deploy $appName"))

    }
```

See how we are passing an application name into our pipeline. The test fails, naturally. So TDD up boys, let's go green. Our script now looks like this

```groovy
def build(Map config) {
    String appName = config.appName
    stage("build $appName") {
    }
    stage("deploy $appName") {
    }
}
```

So we know that the correct stages exist. Now let's see if they're doing what they're supposed to. This is the real gravy of the test framework.

This test actually examines the interactions in the build stage. Currently, this wil be just to echo something to the effect that we are building the app.

```groovy

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation

@Test
    void buildStageActsAsExpected() {

        String appName = 'Foo Application'

        SimplePipelineDefinition pipeline = testable(SimplePipelineDefinition)

        pipeline.build([appName: appName])

        PipelineRecording recording = pipeline.recording

        StageModel buildStage = recording.getStage("build $appName")

        assertThat(buildStage.codeBlock, hadInvocation("echo", "Building $appName"))

    }
```

This introduces the *hadInvocation* method, another part of the test framework. The test, of course, fails, so we now implement the step.


```groovy
def build(Map config) {
    String appName = config.appName
    stage("build $appName") {
        echo "Building $appName"
    }
    stage("deploy $appName") {
    }
}
```

Simple. As an exercise, write a failing test for the deploy stage, then make it pass.

## The test framework


The test framework is still very much a work in progress. It will expand as needed. Part of the job of writing new pipelines and tests for them will often be to add mocking for new parts of the DSL.