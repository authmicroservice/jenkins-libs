package com.elevenware.jenkins.recording

/**
 * EventBroker
 *
 * Allows for publishing of events across the framework, to avoid labourious dependency wiring
 */
class EventBroker {

    private static final EventBroker INSTANCE = new EventBroker()

    private List listeners

    private EventBroker() {
        listeners = []
    }

    static EventBroker getInstance() {
        INSTANCE
    }

    void registerListener(DslEventListener listener) {
        listeners << listener
    }

    void notifyListeners(DslEvent dslEvent) {
        listeners.each { DslEventListener listener ->
            listener.doReceive(dslEvent)
        }
    }
}
