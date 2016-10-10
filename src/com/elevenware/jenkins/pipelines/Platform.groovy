package com.elevenware.jenkins.pipelines

abstract class Platform {

    private static Map PLATFORMS = [ java: JavaPlatform ]

    static Class<Platform> forType(String type) {
        Class platformClass = PLATFORMS[type]
        if(!platformClass) {
            throw new RuntimeException("No platform for '${type}' defined")
        }
        platformClass
    }

}
