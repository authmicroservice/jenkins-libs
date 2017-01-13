package com.elevenware.jenkins.pipelines


def build(Map config) {
    stage('build') {
        node {
            echo "Building"
        }
    }
    stage('deploy') {
        node {
            echo "Deploying"
        }
    }
}

