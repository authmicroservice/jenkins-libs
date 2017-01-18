package com.elevenware.jenkins.pipelines.definitions

import com.elevenware.jenkins.pipelines.PipelineContext

def run(PipelineContext context) {
    stage('build') {
        node {
            echo "Running build stage for ${context.appName}"
        }
    }
    stage('deploy') {
        node {
            echo "Running deploy for ${context.appName}"
        }
    }

}