package io.streamzi.cloudevents.kafka.util;

import io.streamzi.cloudevents.CloudEvent;
import io.streamzi.cloudevents.CloudEventBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Utility methods for working with CloudEvents and Kafka.
 *
 */
public class KafkaHeaderUtil {

    private static final String EVENT_TYPE = "EventType";
    private static final String CLOUDEVENTS_VERSION = "CloudEventsVersion";
    private static final String SOURCE = "Source";
    private static final String EVENT_ID = "EventID";
    private static final String EVENT_TYPE_VERSION = "EventTypeVersion";
    private static final String SCHEMA_URL = "SchemaURL";
    private static final String CONTENT_TYPE = "ContentType";

    /**
     * Create Kafka Headers from a CloudEvent
     * @param ce Event to extract the headers from
     * @return Headers that can be used to construct a ProducerRecord
     */
    public static Iterable<Header> extractHeaders(final CloudEvent<?> ce) {

        RecordHeaders headers = new RecordHeaders();

        headers.add(new RecordHeader(EVENT_TYPE, ce.getEventType().getBytes()));
        headers.add(new RecordHeader(CLOUDEVENTS_VERSION, ce.getCloudEventsVersion().getBytes()));
        headers.add(new RecordHeader(SOURCE, ce.getSource().toString().getBytes()));
        headers.add(new RecordHeader(EVENT_ID, ce.getEventID().getBytes()));

        if (ce.getEventTypeVersion().isPresent()) {
            headers.add(new RecordHeader(EVENT_TYPE_VERSION, (ce.getEventTypeVersion().get()).getBytes()));
        }

        if (ce.getSchemaURL().isPresent()) {
            headers.add(new RecordHeader(SCHEMA_URL, (ce.getSchemaURL().get()).toString().getBytes()));
        }

        if (ce.getContentType().isPresent()) {
            headers.add(new RecordHeader(CONTENT_TYPE, (ce.getContentType().get()).getBytes()));
        }

        //todo: extensions?

        return headers;
    }

    /**
     * Create a CloudEvent from a message consumed from a Kafka topic. Populates the CloudEvent attributes
     * from the Kafka Headers and the data from the Kafka value.
     * @param record message receieved from Kafka
     * @param <K> Message Key
     * @param <V> Message Value
     * @return CloudEvent representation of the Kafka message
     */
    public static <K, V> CloudEvent readFromConsumerRecord(final ConsumerRecord<K, V> record) {

        Headers headers = record.headers();
        CloudEventBuilder<V> builder = new CloudEventBuilder<>();

        try {

            builder.eventType(new String(headers.lastHeader(EVENT_TYPE).value()));
            builder.cloudEventsVersion(new String(headers.lastHeader(CLOUDEVENTS_VERSION).value()));
            builder.source(new URI(new String(headers.lastHeader(SOURCE).value())));
            builder.eventID(new String(headers.lastHeader(EVENT_ID).value()));

            builder.eventTime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(record.timestamp()), ZoneOffset.UTC));

            if (headers.lastHeader(EVENT_TYPE_VERSION) != null) {
                builder.eventTypeVersion(new String(headers.lastHeader(EVENT_TYPE_VERSION).value()));
            }

            if (headers.lastHeader(SCHEMA_URL) != null) {
                builder.schemaURL(new URI(new String(headers.lastHeader(SCHEMA_URL).value())));
            }
            if (headers.lastHeader(CONTENT_TYPE) != null) {
                builder.contentType(new String(headers.lastHeader(CONTENT_TYPE).value()));
            }

            //todo: add extensions

            builder.data(record.value());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return builder.build();
    }


}
