package com.elevenware.jenkins.pipelines


def build(Map config) {
    def message = config.message
    node {
        stage('build') {
           echo message
        }
    }
}

