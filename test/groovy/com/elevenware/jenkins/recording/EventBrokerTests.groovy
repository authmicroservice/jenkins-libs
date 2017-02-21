package com.elevenware.jenkins.recording

import org.junit.Test

import static org.junit.Assert.assertTrue

class EventBrokerTests {

    @Test
    void observationWorks() {

        boolean invoked = false

        DslEventListener listener = new DslEventListener() {

            @Override
            void doReceive(DslEvent event) {
                invoked = true
            }
        }

        EventBroker.getInstance().registerListener(listener)
        EventBroker.getInstance().notifyListeners(new DslEvent())

        assertTrue(invoked)

    }

}
