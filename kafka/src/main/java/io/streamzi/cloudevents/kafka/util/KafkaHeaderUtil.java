package io.streamzi.cloudevents.kafka.util;

import io.streamzi.cloudevents.CloudEvent;
import io.streamzi.cloudevents.CloudEventBuilder;
import org.aerogear.kafka.serialization.CafdiSerdes;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.Serde;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static io.streamzi.cloudevents.impl.CloudEventImpl.CLOUD_EVENTS_VERSION_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.CONTENT_TYPE_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.EVENT_ID_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.EVENT_TIME_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.EVENT_TYPE_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.EVENT_TYPE_VERSION_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.SCHEMA_URL_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.SOURCE_KEY;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

/**
 * Utility methods for working with CloudEvents and Kafka.
 *
 */
public final class KafkaHeaderUtil {

    private KafkaHeaderUtil() {
        // no-op
    }

    /**
     * Create Kafka Headers from a CloudEvent
     * @param ce Event to extract the headers from
     * @return Headers that can be used to construct a ProducerRecord
     */
    public static Headers extractHeaders(final CloudEvent<?> ce) {

        final RecordHeaders headers = new RecordHeaders();

        headers.add(new RecordHeader(EVENT_TYPE_KEY, ((Serde) CafdiSerdes.serdeFrom(String.class)).serializer().serialize(null, ce.getEventType())));
        headers.add(new RecordHeader(CLOUD_EVENTS_VERSION_KEY, ((Serde) CafdiSerdes.serdeFrom(String.class)).serializer().serialize(null, ce.getCloudEventsVersion())));
        headers.add(new RecordHeader(SOURCE_KEY, ((Serde) CafdiSerdes.serdeFrom(String.class)).serializer().serialize(null, ce.getSource().toString())));
        headers.add(new RecordHeader(EVENT_ID_KEY, ((Serde) CafdiSerdes.serdeFrom(String.class)).serializer().serialize(null, ce.getEventID())));

        if (ce.getEventTypeVersion().isPresent()) {
            headers.add(new RecordHeader(EVENT_TYPE_VERSION_KEY,  ((Serde) CafdiSerdes.serdeFrom(String.class)).serializer().serialize(null, ce.getEventTypeVersion().get()) ));
        }

        if (ce.getSchemaURL().isPresent()) {
            headers.add(new RecordHeader(SCHEMA_URL_KEY, ((Serde) CafdiSerdes.serdeFrom(String.class)).serializer().serialize(null, ce.getSchemaURL().get().toString()) ));
        }

        if (ce.getContentType().isPresent()) {
            headers.add(new RecordHeader(CONTENT_TYPE_KEY, ((Serde) CafdiSerdes.serdeFrom(String.class)).serializer().serialize(null, ce.getContentType().get()) ));
        }

        if (ce.getEventTime().isPresent()) {
            headers.add(new RecordHeader(EVENT_TIME_KEY, ((Serde) CafdiSerdes.serdeFrom(String.class)).serializer().serialize(null, ce.getEventTime().get().toString()) ));
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
    public static <K, V> CloudEvent<Map<K, V>> createFromConsumerRecord(final ConsumerRecord<K, V> record) {

        final Headers headers = record.headers();
        final CloudEventBuilder builder = new CloudEventBuilder();

        try {

            builder.eventType(CafdiSerdes.serdeFrom(String.class).deserializer().deserialize(null, headers.lastHeader(EVENT_TYPE_KEY).value()));
            builder.cloudEventsVersion(CafdiSerdes.serdeFrom(String.class).deserializer().deserialize(null, headers.lastHeader(CLOUD_EVENTS_VERSION_KEY).value()));
            builder.source(URI.create(CafdiSerdes.serdeFrom(String.class).deserializer().deserialize(null, headers.lastHeader(SOURCE_KEY).value())));
            builder.eventID(CafdiSerdes.serdeFrom(String.class).deserializer().deserialize(null, headers.lastHeader(EVENT_ID_KEY).value()));

            if (headers.lastHeader(EVENT_TIME_KEY) != null) {
                builder.eventTime(ZonedDateTime.parse(CafdiSerdes.serdeFrom(String.class).deserializer().deserialize(null, headers.lastHeader(EVENT_TIME_KEY).value()), ISO_ZONED_DATE_TIME));
            }

            if (headers.lastHeader(EVENT_TYPE_VERSION_KEY) != null) {
                builder.eventTypeVersion(CafdiSerdes.serdeFrom(String.class).deserializer().deserialize(null, headers.lastHeader(EVENT_TYPE_VERSION_KEY).value()));
            }

            if (headers.lastHeader(SCHEMA_URL_KEY) != null) {
                builder.schemaURL(URI.create(CafdiSerdes.serdeFrom(String.class).deserializer().deserialize(null, headers.lastHeader(SCHEMA_URL_KEY).value())));
            }

            //todo: add extensions

            // use the pure key/value as the 'data' part
            final Map<K, V> rawKafkaRecord = new HashMap();
            rawKafkaRecord.put(record.key(), record.value());
            builder.data(rawKafkaRecord);

            if (headers.lastHeader(CONTENT_TYPE_KEY) != null) {
                builder.contentType(CafdiSerdes.serdeFrom(String.class).deserializer().deserialize(null, headers.lastHeader(CONTENT_TYPE_KEY).value()));
            } else {
                // if nothing was specified we use a 'custom' content type to describe the data format
                builder.contentType("application/ce-kafka-data"); // todo: move to constant
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

}

