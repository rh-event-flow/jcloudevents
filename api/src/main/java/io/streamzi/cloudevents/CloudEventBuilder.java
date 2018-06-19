package io.streamzi.cloudevents;

import io.streamzi.cloudevents.impl.CloudEventImpl;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

public class CloudEventBuilder<T> {

    private String eventType;
    private String cloudEventsVersion = "0.1";
    private URI source;
    private String eventID;
    private String eventTypeVersion;
    private ZonedDateTime eventTime;
    private URI schemaURL;
    private String contentType;
    private Map extensions;
    private T data;

    public CloudEventBuilder<T> eventType(final String eventType) {
        this.eventType = eventType;
        return this;
    }

    public CloudEventBuilder<T> eventTypeVersion(final String eventTypeVersion) {
        this.eventTypeVersion = eventTypeVersion;
        return this;
    }

    public CloudEventBuilder<T> cloudEventsVersion(final String cloudEventsVersion) {
        this.cloudEventsVersion = cloudEventsVersion;
        return this;
    }

    public CloudEventBuilder<T> source(final URI source) {
        this.source = source;
        return this;
    }

    public CloudEventBuilder<T> eventID(final String eventID) {
        this.eventID = eventID;
        return this;
    }

    public CloudEventBuilder<T> eventTime(final ZonedDateTime eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public CloudEventBuilder<T> schemaURL(final URI schemaURL) {
        this.schemaURL = schemaURL;
        return this;
    }

    public CloudEventBuilder<T> contentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }

    public CloudEventBuilder<T> extensions(final Map extensions) {
        this.extensions = extensions;
        return this;
    }

    public CloudEventBuilder<T> data(final T data) {
        this.data = data;
        return this;
    }

    public CloudEvent<T> build() {

        if (eventType == null || cloudEventsVersion == null || source == null || eventID == null) {
            throw new IllegalArgumentException("please provide all required fields");
        }

        return new CloudEventImpl<T>(eventType, cloudEventsVersion, source, eventID, eventTypeVersion, eventTime, schemaURL, contentType, extensions, data);
    }
}
