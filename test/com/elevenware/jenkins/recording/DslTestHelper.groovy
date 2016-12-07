package com.elevenware.jenkins.recording

class DslTestHelper {

    static def testable(Class scriptClass) {
        def script = scriptClass.newInstance()
        script.metaClass {
            mixin DslDelegate
        }
        return script
    }

}
