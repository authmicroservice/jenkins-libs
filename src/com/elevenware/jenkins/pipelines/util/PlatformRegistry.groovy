package com.elevenware.jenkins.pipelines.util

import com.elevenware.jenkins.pipelines.platforms.SimplePlatform
import com.elevenware.jenkins.pipelines.platforms.JavaPlatform

class PlatformRegistry {

    private static PlatformRegistry INSTANCE = new PlatformRegistry()

    private def registry = [:]

    private PlatformRegistry() {
        registry['java'] = JavaPlatform
        registry['simple'] = SimplePlatform
    }

    static PlatformRegistry getInstance() {
        INSTANCE
    }

    def create(String name) {
        if(!registry.containsKey(name)) {
            throw new RuntimeException("Cannot find platform type '${name}' in '${registry.keySet().join(' ')}'")
        }
        Class platformClass = registry[name]
        platformClass.newInstance()
    }

}
