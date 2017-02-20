package com.elevenware.jenkins.pipelines.platforms

import com.elevenware.jenkins.pipelines.PipelineContext

def build(PipelineContext context) {
    echo "building with maven"
}

getVersion() {
    '1.0.1'
}