package io.streamzi.cloudevents;

import io.streamzi.cloudevents.kafka.util.KafkaHeaderUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.Test;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.UUID;

import static io.streamzi.cloudevents.impl.CloudEventImpl.CLOUD_EVENTS_VERSION_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.EVENT_ID_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.EVENT_TIME_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.EVENT_TYPE_KEY;
import static io.streamzi.cloudevents.impl.CloudEventImpl.SOURCE_KEY;
import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static org.apache.kafka.common.record.TimestampType.CREATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;

public class KafkaHeaderTest {

    @Test
    public void createHeadersFromCloudEvent() {

        final CloudEvent cloudEvent = JsonMapper.fromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("azure.json"));
        final Headers headers = KafkaHeaderUtil.extractHeaders(cloudEvent);

        assertThat(new String(headers.lastHeader(EVENT_ID_KEY).value())).isEqualTo("96fb5f0b-001e-0108-6dfe-da6e2806f124");
        assertThat(new String(headers.lastHeader(EVENT_TYPE_KEY).value())).isEqualTo("Microsoft.Storage.BlobCreated");

        assertThat(headers.lastHeader(EVENT_TIME_KEY)).isNotNull();
        assertThat(new String(headers.lastHeader(EVENT_TIME_KEY).value())).isEqualTo(ZonedDateTime.parse("2018-04-23T12:28:22.4579346Z", ISO_ZONED_DATE_TIME).toString());
    }

    @Test
    public void testConsumerRecord() {

        final RecordHeaders requiredHeaders = new RecordHeaders();
        requiredHeaders.add(EVENT_TYPE_KEY, "MY.CLOUD.CREATE".getBytes());
        requiredHeaders.add(CLOUD_EVENTS_VERSION_KEY, "0.1".getBytes());
        requiredHeaders.add(SOURCE_KEY, URI.create("/senderTopic").toString().getBytes());
        requiredHeaders.add(EVENT_ID_KEY, UUID.randomUUID().toString().getBytes());

        final ConsumerRecord<String, String> record = new ConsumerRecord("topic", 0, 0, System.currentTimeMillis(), CREATE_TIME, 0L, 0, 0, "Key", "Value", requiredHeaders);

        final CloudEvent cloudEvent = KafkaHeaderUtil.createFromConsumerRecord(record);

        assertThat(cloudEvent.getData().get()).isNotNull();
        assertThat(cloudEvent.getData().get()).isEqualTo(record);

        assertThat(cloudEvent.getEventType()).isEqualTo("MY.CLOUD.CREATE");
        assertThat(cloudEvent.getCloudEventsVersion()).isEqualTo("0.1");
        assertThat(cloudEvent.getSource()).isEqualTo(URI.create("/senderTopic"));
        assertThat(cloudEvent.getEventID()).isNotNull();

        assertThat(cloudEvent.getContentType().equals("application/ce-kafka-consumer-record"));
    }
}
