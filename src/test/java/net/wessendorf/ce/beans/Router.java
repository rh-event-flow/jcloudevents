package net.wessendorf.ce.beans;

import net.wessendorf.ce.CloudEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import java.net.URI;
import java.util.UUID;

import static net.wessendorf.ce.CloudEventVersion.ZERO_ONE;

public class Router {

    @Inject
    private Event<CloudEvent> cloudEvent;

    public void routeMe() throws Exception {

        final CloudEvent event = new CloudEvent(
                "Cloud.Storage.Item.Created",
                ZERO_ONE.toString(), new URI("/trigger"),
                UUID.randomUUID().toString());

        cloudEvent.fire(event);
    }
}
