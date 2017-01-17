package com.elevenware.jenkins.pipelines

import com.cloudbees.groovy.cps.NonCPS

class PipelineContext implements Serializable{

    PipelineContext(String pipelineType) {
        this.pipeline = pipelineType
    }

    String appName
    String role
    String platform
    String cookbookName
    String pipeline

    @NonCPS
    void dump(PrintWriter out) {
        out.println "Pipeline type: $pipeline"
        out.println "App Name: $appName"
        out.println "Role: $role"
        out.println "Platform: $platform"
        out.println "Cookbook: $cookbookName"
    }

}
