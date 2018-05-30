package net.wessendorf.ce.beans;

import net.wessendorf.ce.CloudEvent;
import net.wessendorf.ce.CloudEventBuilder;

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

        cloudEvent.fire(event);
    }
}
