package io.streamzi.cloudevents;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.streamzi.cloudevents.impl.CloudEventImpl;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

public final class JsonMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(JsonMapper.class.getName());


    public static CloudEventImpl fromInputStream(final InputStream inputStream) {
        try {
            return MAPPER.readValue(inputStream, CloudEventImpl.class);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            throw new IllegalStateException("input was not parseable", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(ZonedDateTime.parse("2018-04-23T12:28:22.4579346Z"));
    }
}
