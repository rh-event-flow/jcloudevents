package io.streamzi.cloudevents;

import io.streamzi.cloudevents.impl.CloudEventImpl;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapperTest {


    @Test
    public void testAzureJSON() {
        CloudEventImpl ce = JsonMapper.fromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("azure.json"));
        assertThat(ce.getEventType()).isEqualTo("Microsoft.Storage.BlobCreated");
        assertThat(ce.getData()).isNotNull();
    }

    @Test
    public void testAmazonJSON() {
        CloudEventImpl ce = JsonMapper.fromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("aws.json"));
        assertThat(ce.getEventType()).isEqualTo("aws.s3.object.created");
    }
}
