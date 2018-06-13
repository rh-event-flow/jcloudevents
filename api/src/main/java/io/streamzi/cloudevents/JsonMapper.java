package io.streamzi.cloudevents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.streamzi.cloudevents.impl.CloudEventImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public final class JsonMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(JsonMapper.class.getName());

    static {
        MAPPER.registerModule(new Jdk8Module());
    }

    public static CloudEventImpl fromInputStream(final InputStream inputStream) {
        try {
            return MAPPER.readValue(inputStream, CloudEventImpl.class);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            throw new IllegalStateException("input was not parseable", e);
        }
    }
}
