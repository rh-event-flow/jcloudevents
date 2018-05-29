package net.wessendorf.ce.beans;

import net.wessendorf.ce.CloudEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class Observer {

    @Inject
    private Receiver receiver;


    public void receiveCloudEvent(@Observes CloudEvent cloudEvent) {

        receiver.ack();

    }

}
