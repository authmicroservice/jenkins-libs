package com.elevenware.jenkins.pipelines

import com.cloudbees.groovy.cps.NonCPS
import com.elevenware.jenkins.pipelines.deploy.DeployStrategy
import com.elevenware.jenkins.pipelines.elements.AutoDeployElement
import com.elevenware.jenkins.pipelines.elements.BuildTestPublishElement
import com.elevenware.jenkins.pipelines.elements.PipelineElement
import com.elevenware.jenkins.pipelines.elements.StopElement


class GithubPipeline extends Pipeline implements Serializable {


    GithubPipeline(Platform platform, DeployStrategy deployStrategy) {
        super(platform, deployStrategy)
    }

    @Override
    PipelineElement getStart() {
        def prod = new AutoDeployElement("Production", StopElement.instance())
        def staging = new AutoDeployElement("Staging", prod)
        def qa = new AutoDeployElement("QA", staging)
        def integration = new AutoDeployElement("Integration", qa)
        new BuildTestPublishElement(integration)

    }

    @Override
    @NonCPS
    List getElements() {
        [
//                new BuildTestPublishElement(),
                new AutoDeployElement("Integration"),
                new AutoDeployElement("QA"),
                new AutoDeployElement("Staging"),
                new AutoDeployElement("Production")
        ]
    }
}
