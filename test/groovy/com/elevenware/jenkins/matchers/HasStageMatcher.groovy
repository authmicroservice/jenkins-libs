package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.recording.PipelineRecording
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

class HasStageMatcher extends BaseMatcher<PipelineRecording> {

    private String stageName
    private PositionMatcher positionMatcher

    HasStageMatcher(String stageName, PositionMatcher positionMatcher) {
        this.stageName = stageName
        this.positionMatcher = positionMatcher
    }

    @Override
    boolean matches(Object o) {
        PipelineRecording recording = (PipelineRecording) o

        boolean exists = (recording.getStage(stageName) != null)

        boolean inPosition = positionMatcher.matches(stageName, recording)

        exists && inPosition


    }

    @Override
    void describeTo(Description description) {

    }

    static class PositionMatcher {

        private int position
        private boolean anyMode = false

        PositionMatcher() {
            this.anyMode = true
        }

        PositionMatcher(int position) {
            this.position = position
        }

        boolean matches(String stageName, PipelineRecording recording) {
            if(anyMode) {
                return true
            }
            Map stages = recording.stages
            List stageList = new ArrayList(stages.keySet())
            if(stageList.size() < position) {
                return false
            }
            def actualStage = stageList.get(position)
            return actualStage == stageName
        }

        static PositionMatcher anyPosition() {
            new PositionMatcher()
        }

    }
}
