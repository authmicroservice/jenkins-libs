package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.matchers.HasStageMatcher.PositionMatcher
import com.elevenware.jenkins.recording.PipelineRecording
import com.elevenware.jenkins.recording.StageModel
import org.hamcrest.Matcher

class DslMatchers {

    static Matcher<StageModel> hadInvocation(String command, Object...args) {
        return new HadInvocationMatcher(command, args)
    }

    static Matcher<CharSequence> isString(String expected) {
        return new IsStringMatcher(expected)
    }

    static Matcher<PipelineRecording> hasStage(String stageName) {
        return hasStage(stageName, PositionMatcher.anyPosition())
    }

    static Matcher<PipelineRecording> hasStage(String stageName, HasStageMatcher.PositionMatcher positionMatcher) {
        return new HasStageMatcher(stageName, positionMatcher)
    }

    static HasStageMatcher.PositionMatcher inPosition(int position) {
        return new HasStageMatcher.PositionMatcher(position)
    }

    static HasStageMatcher.PositionMatcher inFirstPosition() {
        return inPosition(0)
    }

}
