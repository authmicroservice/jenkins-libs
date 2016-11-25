package com.elevenware.jenkins.pipelines.definitions

void inStage(String stageName, Closure closure) {
    stage(stageName) {
        closure.call()
    }
}

void checkout() {
    node {
        echo "Checking out ${scm.key}"
        checkout scm
    }
}

void deploy(String env) {
    node {
        echo "Deploying to ${env}"
    }
}