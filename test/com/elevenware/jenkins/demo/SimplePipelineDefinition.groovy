package com.elevenware.jenkins.demo


def build(Map config) {
    String appName = config.appName
    stage("build $appName") {
        // TODO: allow for nesting of stages in nodes and nodes in stages
//        node {
            echo "Building $appName"
//        }
    }
    stage("deploy $appName") {
//        node {
            echo "Deploying $appName"
//        }
    }
}

