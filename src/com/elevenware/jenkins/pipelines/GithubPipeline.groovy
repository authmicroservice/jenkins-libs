package com.elevenware.jenkins.pipelines

import com.cloudbees.groovy.cps.NonCPS
import com.elevenware.jenkins.pipelines.elements.AutoDeployElement
import com.elevenware.jenkins.pipelines.elements.BuildTestPublishElement


class GithubPipeline extends Pipeline implements Serializable {


    GithubPipeline(Platform platform) {
        super(platform)
    }

    @Override
    @NonCPS
    List getElements() {
        [
                new BuildTestPublishElement(),
                new AutoDeployElement("Integration"),
                new AutoDeployElement("QA"),
                new AutoDeployElement("Staging"),
                new AutoDeployElement("Production")
        ]
    }
}
