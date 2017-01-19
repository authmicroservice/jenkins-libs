package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext

def run(PipelineContext context) {
    String name = context.appName
    stage("build $name") {
//        node {
        echo "Running build stage for ${context.appName}"
//        }
    }
    stage("deploy $name") {
//        node {
        echo "Running deploy stage for ${context.appName}"
//        }
    }

}