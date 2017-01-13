package com.elevenware.jenkins.pipelines


def build(Map config) {
    stage('build') {
        // TODO: allow for nesting of stages in nodes and nodes in stages
//        node {
            echo "Building"
//        }
    }
    stage('deploy') {
//        node {
            echo "Deploying"
//        }
    }
}

