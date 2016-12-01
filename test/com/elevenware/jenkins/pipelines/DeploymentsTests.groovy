package com.elevenware.jenkins.pipelines

import com.elevenware.jenkins.pipelines.functions.Deployments
import com.elevenware.jenkins.recording.DslDelegate
import org.junit.Test

class DeploymentsTests {

    @Test
    void something() {

        Deployments deployments = new Deployments()
        deployments.metaClass {

            node { Closure inner ->
                inner.setDelegate(this)
                inner.call()
            }

            libraryResource {

            }

            stage { String stageName, Closure inner ->
                inner.setDelegate(this)
                inner.call()
            }

            echo { String text ->

            }

        }

        deployments.deploy("integration", [role: 'foo'])

    }

    @Test
    void withDelegate() {

//        Deployments.mixin(DslDelegate)
        Deployments deployments = new Deployments()
        deployments.metaClass {
            mixin DslDelegate
        }

        deployments.deploy("integration", [role: 'foo'])

        assert deployments.recordings.size() == 1

    }

}
