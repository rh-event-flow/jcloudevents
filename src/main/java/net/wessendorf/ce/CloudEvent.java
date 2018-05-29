package net.wessendorf.ce;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.Map;

public class CloudEvent implements Serializable {

    private static final long serialVersionUID = 18763219239181838L;

    private final String eventType;
    private final String cloudEventsVersion;
    private final URI source;
    private final String eventID;
    private String eventTypeVersion;
    private Date eventTime;
    private URI schemaURL;

    public CloudEvent(final String eventType, final String cloudEventsVersion, final URI source, final String eventID) {
        this.eventType = eventType;
        this.cloudEventsVersion = cloudEventsVersion;
        this.source = source;
        this.eventID = eventID;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventTypeVersion() {
        return eventTypeVersion;
    }

    public void setEventTypeVersion(String eventTypeVersion) {
        this.eventTypeVersion = eventTypeVersion;
    }

    public String getCloudEventsVersion() {
        return cloudEventsVersion;
    }

    public URI getSource() {
        return source;
    }

    public String getEventID() {
        return eventID;
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

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    private String contentType;
    private Map extensions;
    private Map data;
}