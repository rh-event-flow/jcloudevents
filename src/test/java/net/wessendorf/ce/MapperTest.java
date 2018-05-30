package net.wessendorf.ce;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapperTest {


    @Test
    public void testAzureJSON() {
        CloudEvent ce = JsonMapper.fromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("azure.json"));
        assertThat(ce.getEventType()).isEqualTo("Microsoft.Storage.BlobCreated");
    }

    @Test
    public void testAmazonJSON() {
        CloudEvent ce = JsonMapper.fromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("aws.json"));
        assertThat(ce.getEventType()).isEqualTo("aws.s3.object.created");
    }
}
