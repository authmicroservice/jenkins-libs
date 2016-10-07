package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.elements.BuildTestPublishElement


class GithubPipeline extends Pipeline implements Serializable {


    @Override
    @NonCPS
    List getElements() {
        [new BuildTestPublishElement()]
    }
}
