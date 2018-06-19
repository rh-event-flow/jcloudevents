package io.streamzi.cloudevents;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomEventTypesTests {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.registerModule(new Jdk8Module());
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
    }

    @Test
    public void testBinding() throws IOException {

        // given
        final Map<String, Object> storagePayload = (MAPPER.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("pvc.json"), Map.class));
        final CloudEvent<Map<String, Object>> storageCloudEventWrapper = new CloudEventBuilder<Map<String, Object>>()
                .eventType("ProvisioningSucceeded")
                .source(URI.create("/scheduler"))
                .eventID(UUID.randomUUID().toString())
                .data(storagePayload)
                .build();

        // when
        final String httpSerializedPayload = MAPPER.writeValueAsString(storageCloudEventWrapper);
        assertThat(httpSerializedPayload).contains("PersistentVolumeClaim");
        //PARSE into real object, on the other side
        final CloudEvent<GlusterVolumeClaim> event = MAPPER.readValue(httpSerializedPayload, new TypeReference<CloudEvent<GlusterVolumeClaim>>() {});

        // then
        assertThat(event.getData().get()).isNotNull();
        assertThat(event.getData().get().getSpec().getCapacity().get("storage")).isEqualTo("2Gi");
        assertThat(event.getData().get().getSpec().getAccessModes()).containsExactly("ReadWriteMany");
    }
}
