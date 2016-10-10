package com.elevenware.jenkins.pipelines.definitions

void build() {
    sh "./build.sh"
}

void test() {
    sh "./test.sh"
}

void publish() {
    sh "./publish.sh"
}