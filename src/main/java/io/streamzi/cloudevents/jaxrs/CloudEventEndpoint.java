package io.streamzi.cloudevents.jaxrs;

import io.streamzi.cloudevents.CloudEvent;
import io.streamzi.cloudevents.EventTypeQualifier;
import io.streamzi.cloudevents.impl.CloudEventImpl;


import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ce")
public class CloudEventEndpoint {

    @Inject
    private Event<CloudEvent<?>> cloudEventSource;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(final CloudEventImpl ce) {

        // dispatch to CDI
        cloudEventSource.select(new EventTypeQualifier(ce.getEventType())).fire(ce);

        return Response.status(Response.Status.ACCEPTED).entity(ce).build();
    }

}
