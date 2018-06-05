package io.streamzi.cloudevents.beans;

import io.streamzi.cloudevents.CloudEvent;
import io.streamzi.cloudevents.CloudEventBuilder;
import io.streamzi.cloudevents.EventTypeQualifier;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import java.net.URI;
import java.util.UUID;

public class Router {

    @Inject
    private Event<CloudEvent<MyCustomEvent>> cloudEvent;

    public void routeMe() throws Exception {

        CloudEvent<MyCustomEvent> event = new CloudEventBuilder<MyCustomEvent>()
                .eventType("Cloud.Storage.Item.Created")
                .source(new URI("/trigger"))
                .eventID(UUID.randomUUID().toString())
                .build();

        cloudEvent.select(
                    new EventTypeQualifier("Cloud.Storage.Item.Created"))
                .fire(event);
    }
}
