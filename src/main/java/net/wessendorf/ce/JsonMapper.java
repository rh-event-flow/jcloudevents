package net.wessendorf.ce;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public final class JsonMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(JsonMapper.class.getName());


    public static CloudEvent fromInputStream(final InputStream inputStream) {
        try {
            return MAPPER.readValue(inputStream, CloudEvent.class);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            throw new IllegalStateException("input was not parseable", e);
        }
    }
}
