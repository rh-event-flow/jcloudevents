package net.wessendorf.ce.impl;

import net.wessendorf.ce.CloudEvent;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.Map;

public class CloudEventImpl<T> implements CloudEvent<T>, Serializable {

    private static final long serialVersionUID = 1;

    private String eventType;
    private String cloudEventsVersion;
    private URI source;
    private String eventID;
    private String eventTypeVersion;
    private Date eventTime;
    private URI schemaURL;
    private String contentType;
    private Map extensions;
    private T data;

    public CloudEventImpl(final String eventType, final String cloudEventsVersion, final URI source, final String eventID, final String eventTypeVersion, final Date eventTime, final URI schemaURL, final String contentType, final Map extensions, final T data) {
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
    public String getEventTypeVersion() {
        return eventTypeVersion;
    }

    @Override
    public Date getEventTime() {
        return eventTime;
    }

    @Override
    public URI getSchemaURL() {
        return schemaURL;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public Map getExtensions() {
        return extensions;
    }

    @Override
    public T getData() {
        return data;
    }
}