package com.elevenware.jenkins.recording

import org.junit.Test

import static com.elevenware.jenkins.matchers.DslMatchers.hadInvocation
import static org.hamcrest.MatcherAssert.assertThat

class DslDelegateTests {

    @Test
    void defaultStageUsed() {

        DslDelegate delegate = new DslDelegate()

        delegate.node {
            echo "hello"
        }

        assertThat(delegate.recording.defaultStage().codeBlock, hadInvocation("echo", "hello"))

    }

    @Test
    void namedStage() {
        DslDelegate delegate = new DslDelegate()

        delegate.stage('stage1') {
            echo "hello"
        }

        assertThat(delegate.recording.getStage("stage1").codeBlock, hadInvocation("echo", "hello"))
    }

    @Test
    void stageInsideNode() {
        DslDelegate delegate = new DslDelegate()

        delegate.run {
            stage('stage1') {
                node {
                    echo "hello"
                }
            }
        }

        assertThat(delegate.recording.getStage('stage1').codeBlock, hadInvocation("echo", "hello"))
    }

    @Test
    void nodeInsideStage() {
        DslDelegate delegate = new DslDelegate()

        delegate.run {
            node {
                stage('stage1') {
                    echo "hello"
                }
            }
        }

        assertThat(delegate.recording.getStage('stage1').codeBlock, hadInvocation("echo", "hello"))
    }

}
