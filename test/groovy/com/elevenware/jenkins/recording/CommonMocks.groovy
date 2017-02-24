package com.elevenware.jenkins.recording

import com.elevenware.jenkins.recording.dsl.DslStub
import org.hamcrest.collection.IsMapContaining

import static com.elevenware.jenkins.matchers.DslMatchers.isString
import static com.elevenware.jenkins.matchers.DslMatchers.matchesRegex
import static org.mockito.Mockito.when
import static org.mockito.hamcrest.MockitoHamcrest.argThat

class CommonMocks {

   static void mockCurrentAppSpec(String cookbookName, String spec, String environment) {
    when(DslStub.INSTANCE.sh((Map) argThat(new IsMapContaining(isString("script"), matchesRegex("bundle exec knife environment show ${environment}"))))).thenReturn("""
  { "${environment}": {
    "cookbook_versions.${cookbookName}": "${spec}"
  }
}""")
    }

}
