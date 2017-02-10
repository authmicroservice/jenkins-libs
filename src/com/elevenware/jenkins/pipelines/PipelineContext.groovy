package com.elevenware.jenkins.pipelines

import com.cloudbees.groovy.cps.NonCPS

class PipelineContext implements Serializable{

    String appName
    String role
    String platform
    String cookbookName
    def platformImplementation

    @NonCPS
    void dump(PrintStream out) {
        out.println "App Name: $appName"
        out.println "Role: $role"
        out.println "Platform: $platform"
        out.println "Cookbook: $cookbookName"
        out.flush()
    }

}
