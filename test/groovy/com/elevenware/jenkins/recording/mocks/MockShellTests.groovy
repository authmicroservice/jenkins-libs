package com.elevenware.jenkins.recording.mocks

import org.junit.Test

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

class MockShellTests {

    @Test
    void simpleShCommandWorks() {

        String path = '/usr/bin'
        int responseCode = 0

        MockShell shell = new MockShell()
        shell.whenCommand('sh "echo $PATH"').thenRespond(responseCode).withStdout(path)

        Response response = shell.invoke('sh "echo $PATH"')

        assertThat(response.stdout, equalTo(path))
        assertThat(response.responseCode, equalTo(responseCode))

    }

    @Test
    void unexpectedCommandsPass() {

        MockShell shell = new MockShell()

        Response response = shell.invoke('whoami')

        assertThat(response.responseCode, equalTo(0))

    }

    @Test
    void regexesWork() {

        MockShell shell = new MockShell()
        shell.whenCommand('ls foo').thenRespond(0).withStdout('foo')
        shell.whenCommand(~/ls .*bar/).thenRespond(1)

        Response response = shell.invoke('ls lolbar')

        assertThat(response.responseCode, equalTo(1))

    }

}
