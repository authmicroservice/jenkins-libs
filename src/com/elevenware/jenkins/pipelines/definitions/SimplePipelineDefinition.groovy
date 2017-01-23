package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext

def run(PipelineContext context) {
    node {
        stage('checkout') {
            checkout scm: scm, poll: true
        }
    }
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