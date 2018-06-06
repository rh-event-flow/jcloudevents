# CloudEvents with Jakarta EE and the Eclipse Microprofile

The [CloudEvent specification](https://github.com/cloudevents/spec) is vendor-neutral specification for defining the format of event data that is being exchanged between different cloud systems. The specification basically defines an abstract envelope for any event data payload, without knowing specific implementation details of the actual underlying event. The current version of the spec is at `0.1` and it describes a simple event format, which was demonstrated at last [KubeCon 2018](https://youtu.be/TZPPjAv12KU) using different _Serverless platforms_, such as [Apache Openwhisk](https://github.com/apache/incubator-openwhisk).

## Java API

Based on the specification we started to look at an early implementation of the API for [Java and CDI](https://github.com/project-streamzi/jcloudevents). Using the API your backend application can create typed CloudEvents, such as:

```java
CloudEvent<MyCustomEvent> event = new CloudEventBuilder<MyCustomEvent>()
    .eventType("My.Cloud.Storage.Item.Created")
    .source(new URI("/trigger"))
    .eventID(UUID.randomUUID().toString())
    .build();
```

In _Enterprise Java_ applications, implemented with Jakarta EE or the Eclipse Microprofile, it's trivial to combine this API with CDI. Application developers can now fire a CloudEvent for further processing inside of the application:

```java
cloudEvent.select(
    new EventTypeQualifier("My.Cloud.Storage.Item.Created"))
.fire(event);
```

This will basically _route_ the event to an Observer Bean, that is interested in the _specific_ event type:

```java
public void receiveCloudEvent(
  @Observes @EventType(name = "My.Cloud.Storage.Item.Created") CloudEvent cloudEvent) {
  // handle the event
}                                                                                       
```

## JAX-RS Bridge

The library contains a JAX-RS endpoint that is able to receive CloudEvent payloads, and fires a CDI event, so that an application just needs to define their listeners like:

```java
public class MultiCloudMonitor {

    public void receiveCloudEventFromAWS(@Observes @EventType(name = "aws.s3.object.created") CloudEvent<?> cloudEvent) {

        // process the AWS event...
    }
    public void receiveCloudEventFromAzure(@Observes @EventType(name = "Microsoft.Storage.BlobCreated") CloudEvent<?> cloudEvent) {

        // process the Azure event...
    }
}
```

This allows developers to register their Microprofile or JakartaEE application for consumption of events, triggered by different cloud providers!

## Outlook

The new reactive messaging specification from the the Eclipse Microprofile initiative will model its own eventing metadata after the CloudEvent specification as an abstraction mechanism for events and their actual transports!  

