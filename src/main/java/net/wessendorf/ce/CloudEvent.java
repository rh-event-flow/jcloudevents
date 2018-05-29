package net.wessendorf.ce;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.Map;

public class CloudEvent implements Serializable {

    private static final long serialVersionUID = 18763219239181838L;

    private String eventType;
    private String eventTypeVersion;
    private String cloudEventsVersion;
    private URI source;
    private String eventID;
    private Date eventTime;
    private URI schemaURL;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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