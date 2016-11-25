package com.elevenware.jenkins

import hudson.model.Result
import org.junit.Rule
import org.junit.Test
import org.jvnet.hudson.test.JenkinsRule
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class DslTests {

    @Rule JenkinsRule jenkins = new JenkinsRule()

    @Test
    void test() {

        jenkins.jenkins.getInjector().injectMembers(this)
        CpsFlowDefinition flow = new CpsFlowDefinition("assert evaluate('1+2+3')==6; echo 'lol'");

        createExecution(flow);
        exec.start();
        exec.waitForSuspension();

        assertTrue(exec.isComplete());
        assertEquals(dumpError(), Result.SUCCESS, exec.getResult());
    }

}
