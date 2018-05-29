package net.wessendorf.ce.beans;

import net.wessendorf.ce.CloudEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class Router {

    @Inject
    private Event<CloudEvent> cloudEvent;

    public void routeMe() {
        cloudEvent.fire(new CloudEvent());
    }
}
