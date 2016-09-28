package com.elevenware.jenkins.pipelines

def create(String name, String env) {
    stage(name: env) {
        echo "Running ${env} for ${name} app"
        echo "SCM $scm"
    }
}