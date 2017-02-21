package com.elevenware.jenkins.recording

import static com.elevenware.jenkins.matchers.MapMatchers.hasValues
import static org.mockito.ArgumentMatchers.argThat
import static org.mockito.Mockito.when

class CommonMocks {

    static void mockCurrentAppSpec(String cookbookName, String spec) {
        when(DslStub.INSTANCE.sh((Map) argThat(hasValues([returnStdout: true])))).thenReturn("""
  { "integration": {
    "cookbook_versions.${cookbookName}": "${spec}"
  }
}""")
    }

}
