package io.streamzi.cloudevents.kafka.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.cloudevents.impl.DefaultCloudEventImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public final class JsonMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(JsonMapper.class.getName());

    static {
        MAPPER.registerModule(new Jdk8Module());
    }

    public static DefaultCloudEventImpl fromInputStream(final InputStream inputStream) {
        try {
            return MAPPER.readValue(inputStream, DefaultCloudEventImpl.class);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            throw new IllegalStateException("input was not parseable", e);
        }
    }
}
