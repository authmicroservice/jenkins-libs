package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.deploy.DeployStrategy
import com.elevenware.jenkins.pipelines.deploy.GenericDeployStrategy

class PipelineBuilder implements Serializable {

    private static Map DEPLOY_STRATEGIES = [ generic: GenericDeployStrategy ]

    private Class<Pipeline> myPipelineClass
    private Class<Platform> myPlatformClass
    private Class<DeployStrategy> myDeployStrategyClass

    void createFlow(String flowType) {
        this.myPipelineClass = Pipeline.forType(flowType)
    }

    void createPlatform(String platform) {
        this.myPlatformClass = Platform.forType(platform)
    }

    void createDeployStrategy(String type) {
        this.myDeployStrategyClass = findOrDie(DEPLOY_STRATEGIES, type, "deploy strategy")
    }

    Pipeline getPipeline() {
        Platform platform = myPlatformClass ? myPlatformClass.newInstance() : new BasicPlatform()
        DeployStrategy deployStrategy = myDeployStrategyClass ? myDeployStrategyClass.newInstance() : new GenericDeployStrategy()
        myPipelineClass.newInstance(platform, deployStrategy)
    }

    private Class findOrDie(Map config, String type, String obituary) {
        Class clazz = config[type]
        if(!clazz) {
            throw new RuntimeException("Unable to find ${obituary} for type '${type}'")
        }
    }

}
