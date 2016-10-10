package com.elevenware.jenkins.pipelines

abstract class Platform implements Serializable {

    private static Map PLATFORMS = [ java: JavaPlatform ]

    @NonCPS
    abstract void build()
    @NonCPS
    abstract void test()
    @NonCPS
    abstract void publish()

    static Class<Platform> forType(String type) {
        Class platformClass = PLATFORMS[type]
        if(!platformClass) {
            throw new RuntimeException("No platform for '${type}' defined")
        }
        platformClass
    }

}
