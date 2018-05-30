package net.wessendorf.ce;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.Map;

public class CloudEvent<T> implements Serializable {

    private static final long serialVersionUID = 18763219239181838L;

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

    CloudEvent() {

    }

    public CloudEvent(final String eventType, final String cloudEventsVersion, final URI source, final String eventID) {
        this(eventType, cloudEventsVersion, source, eventID, null);
    }

    public CloudEvent(final String eventType, final String cloudEventsVersion, final URI source, final String eventID, final T data) {
        this.eventType = eventType;
        this.cloudEventsVersion = cloudEventsVersion;
        this.source = source;
        this.eventID = eventID;
        this.data = data;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getCloudEventsVersion() {
        return cloudEventsVersion;
    }

    public void setCloudEventsVersion(String cloudEventsVersion) {
        this.cloudEventsVersion = cloudEventsVersion;
    }

    public URI getSource() {
        return source;
    }

    public void setSource(URI source) {
        this.source = source;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventTypeVersion() {
        return eventTypeVersion;
    }

    public void setEventTypeVersion(String eventTypeVersion) {
        this.eventTypeVersion = eventTypeVersion;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public URI getSchemaURL() {
        return schemaURL;
    }

    public void setSchemaURL(URI schemaURL) {
        this.schemaURL = schemaURL;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map getExtensions() {
        return extensions;
    }

    public void setExtensions(Map extensions) {
        this.extensions = extensions;
    }

    public T getData() {
        return data;
    }
}