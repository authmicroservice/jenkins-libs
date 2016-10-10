package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.elements.BuildTestPublishElement


class GithubPipeline extends Pipeline implements Serializable {


    GithubPipeline(Platform platform) {
        super(platform)
    }

    @Override
    @NonCPS
    List getElements() {
        [
                new BuildTestPublishElement()
        ]
    }
}
