package com.elevenware.jenkins.pipelines.definitions

void build() {
    node {
        sh "./build.sh"
    }
}

void test() {
    node {
        sh "./test.sh"
    }
}

void publish() {
    node {
        sh "./publish.sh"
    }
}