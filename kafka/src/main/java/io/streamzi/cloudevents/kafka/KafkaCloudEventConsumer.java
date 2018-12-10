package io.streamzi.cloudevents.kafka;

import io.cloudevents.CloudEvent;
import io.cloudevents.cdi.EventTypeQualifier;
import org.aerogear.kafka.cdi.annotation.Consumer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@KafkaConfig(bootstrapServers = "#{KAFKA_SERVICE_HOST}:#{KAFKA_SERVICE_PORT}")
public class KafkaCloudEventConsumer {

    private static final Logger logger = Logger.getLogger(KafkaCloudEventConsumer.class.getName());

    @Inject
    private Event<CloudEvent<?>> cloudEventSource;


    @Consumer(topics = "#{CLOUD_EVENT_BRIDGE_TOPIC}", groupId = "jCloudEvent_group")
    public void eventBridge(final CloudEvent<?> ce) {

        final String eventType = ce.getType();
        logger.info("Processing CloudEvent type: " + eventType);


        // dispatch to CDI
        try {
            cloudEventSource.select(new EventTypeQualifier(eventType)).fire(ce);
            logger.info("Dispatched to CDI event system");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error submitting CDI event", e);
        }
    }
}
