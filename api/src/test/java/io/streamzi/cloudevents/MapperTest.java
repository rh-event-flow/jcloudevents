package io.streamzi.cloudevents;

import io.streamzi.cloudevents.impl.CloudEventImpl;
import org.junit.Test;

import java.net.URI;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;

public class MapperTest {


    @Test
    public void testAzureJSON() {
        CloudEventImpl ce = JsonMapper.fromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("azure.json"));
        assertThat(ce.getEventType()).isEqualTo("Microsoft.Storage.BlobCreated");
        assertThat(ce.getAttributes().get(CloudEventImpl.EVENT_TYPE_KEY)).isEqualTo("Microsoft.Storage.BlobCreated");
        assertThat(ce.getData()).isNotNull();
    }

    @Test
    public void testAmazonJSON() {
        CloudEventImpl ce = JsonMapper.fromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("aws.json"));
        assertThat(ce.getEventType()).isEqualTo("aws.s3.object.created");
        assertThat(ce.getSource().equals(URI.create("https://serverless.com")));
        assertThat(ce.getEventTime().get()).isEqualTo(ZonedDateTime.parse("2018-04-26T14:48:09.769Z", ISO_ZONED_DATE_TIME));
    }
}
