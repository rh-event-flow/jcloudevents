package io.streamzi.cloudevents.cdi;

import javax.enterprise.util.AnnotationLiteral;

public class EventTypeQualifier extends AnnotationLiteral<EventType> implements EventType {

    private static final long serialVersionUID = 1L;
    private final String eventName;

    public EventTypeQualifier(final String name) {
        eventName=name;
    }

    @Override
    public String name() {
        return eventName;
    }
}
