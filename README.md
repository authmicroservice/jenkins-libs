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

Let's build a simple pipeline.