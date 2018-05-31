package io.streamzi.cloudevents.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.streamzi.cloudevents.CloudEvent;

import java.io.Serializable;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class CloudEventImpl<T> implements CloudEvent<T>, Serializable {

    private static final long serialVersionUID = 1;

    private String eventType;
    private String cloudEventsVersion;
    private URI source;
    private String eventID;
    private String eventTypeVersion;

    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime eventTime;
    private URI schemaURL;
    private String contentType;
    private Map extensions;
    private T data;

    public CloudEventImpl(final String eventType, final String cloudEventsVersion, final URI source, final String eventID, final String eventTypeVersion, final ZonedDateTime eventTime, final URI schemaURL, final String contentType, final Map extensions, final T data) {
        this.eventType = eventType;
        this.cloudEventsVersion = cloudEventsVersion;
        this.source = source;
        this.eventID = eventID;
        this.eventTypeVersion = eventTypeVersion;
        this.eventTime = eventTime;
        this.schemaURL = schemaURL;
        this.contentType = contentType;
        this.extensions = extensions;
        this.data = data;
    }
    CloudEventImpl() {

    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public String getCloudEventsVersion() {
        return cloudEventsVersion;
    }

    @Override
    public URI getSource() {
        return source;
    }

    @Override
    public String getEventID() {
        return eventID;
    }

    @Override
    public Optional<String> getEventTypeVersion() {
        return Optional.of(eventTypeVersion);
    }

    @Override
    public Optional<ZonedDateTime> getEventTime() {
        return Optional.of(eventTime);
    }

    @Override
    public Optional<URI> getSchemaURL() {
        return Optional.of(schemaURL);
    }

    @Override
    public Optional<String> getContentType() {
        return Optional.of(contentType);
    }

    @Override
    public Optional<Map> getExtensions() {
        return Optional.of(extensions);
    }

    @Override
    public Optional<T> getData() {
        return Optional.of(data);
    }
}