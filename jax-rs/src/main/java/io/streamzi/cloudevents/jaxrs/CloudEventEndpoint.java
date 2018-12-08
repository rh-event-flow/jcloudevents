package io.streamzi.cloudevents.jaxrs;

import io.cloudevents.CloudEvent;
import io.cloudevents.impl.DefaultCloudEventImpl;
import io.streamzi.cloudevents.cdi.EventTypeQualifier;


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
    public Response hello(final DefaultCloudEventImpl ce) {

        // dispatch to CDI
        cloudEventSource.select(new EventTypeQualifier(ce.getEventType())).fire(ce);

        return Response.status(Response.Status.ACCEPTED).entity(ce).build();
    }

}
