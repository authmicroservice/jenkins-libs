package com.elevenware.jenkins.matchers

import org.mockito.ArgumentMatcher

class MapMatchers implements ArgumentMatcher {

    private Map arg

    MapMatchers(Map arg) {
        this.arg = arg
    }

    @Override
    boolean matches(Object argument) {
        if(!Map.isAssignableFrom(argument.getClass())) {
            return false
        }
        Map actual = (Map) argument
        for(Map.Entry entry: arg.entrySet()) {
            if(!actual.containsKey(entry.key)) {
                return false
            }
            if(!actual.get(entry.key).equals(entry.value)) {
                return false
            }
        }
        return true
    }

    static ArgumentMatcher hasValues(Map arg) {
        return new MapMatchers(arg)
    }

}
