package net.wessendorf.ce.beans;

import net.wessendorf.ce.impl.CloudEventImpl;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class Observer {

    @Inject
    private Receiver receiver;


    public void receiveCloudEvent(@Observes CloudEventImpl cloudEvent) {

        receiver.ack();

    }

}
