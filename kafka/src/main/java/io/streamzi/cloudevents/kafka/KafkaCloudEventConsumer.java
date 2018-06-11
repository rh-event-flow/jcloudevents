package io.streamzi.cloudevents.kafka;

import io.streamzi.cloudevents.CloudEvent;
import io.streamzi.cloudevents.cdi.EventTypeQualifier;
import io.streamzi.cloudevents.impl.CloudEventImpl;
import org.aerogear.kafka.cdi.annotation.Consumer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@KafkaConfig(bootstrapServers = "#{KAFKA_SERVICE_HOST}:#{KAFKA_SERVICE_PORT}")
public class KafkaCloudEventConsumer {

    private static final Logger logger = Logger.getLogger(KafkaCloudEventConsumer.class.getName());

    @Inject
    private Event<CloudEvent<?>> cloudEventSource;


    @Consumer(topics = "#{CLOUD_EVENT_BRIDGE_TOPIC}", groupId = "jCloudEvent_group")
    public void eventBridge(final CloudEventImpl ce) {

        final String eventType = ce.getEventType();
        logger.info("Processing CloudEvent type: " + eventType);

        // add some processor metadata
        final Optional<Map> extensionMap = ce.getExtensions();
        extensionMap.get().put("processor", "kafka-cdi");

        // dispatch to CDI
        cloudEventSource.select(new EventTypeQualifier(eventType)).fire(ce);
    }

}
