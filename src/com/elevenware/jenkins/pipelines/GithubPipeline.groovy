package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.elements.BuildTestPublishElement


class GithubPipeline extends Pipeline implements Serializable {


    @Override
    List getElements() {
        [new BuildTestPublishElement()]
    }
}
