package com.elevenware.jenkins.matchers

import com.elevenware.jenkins.matchers.HasStageMatcher.PositionMatcher
import com.elevenware.jenkins.recording.dsl.PipelineRecording
import com.elevenware.jenkins.recording.dsl.StageModel
import org.hamcrest.Matcher

class DslMatchers {

    static Matcher matchesRegex(String regex) {
        return new RegexMatcher(regex)
    }

    static HadInvocationMatcher hadInvocation(String command) {
        return new HadInvocationMatcher(command)
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

    static ArgumentCaptureWrapper captureTo(ArgumentCapture capture) {
        return new ArgumentCaptureWrapper(capture)
    }
}
