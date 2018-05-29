package net.wessendorf.ce.beans;
import org.mockito.Mockito;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class MockProvider {

    private Receiver receiver = Mockito.mock(Receiver.class);

    @Produces
    public Receiver receiver() {
        return receiver;
    }
}