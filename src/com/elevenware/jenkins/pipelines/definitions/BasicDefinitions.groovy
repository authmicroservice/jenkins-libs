package com.elevenware.jenkins.pipelines.definitions

void checkout() {
    echo "Checking out ${scm.key}"
    checkout scm
}