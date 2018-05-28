package net.wessendorf.ce;

import java.net.URI;
import java.util.Date;
import java.util.Map;

public class CloudEvent {

    private String eventType;
    private String eventTypeVersion;
    private String cloudEventsVersion;
    private URI source;
    private String eventID;
    private Date eventTime;
    private URI schemaURL;
    private String contentType;
    private Map extensions;
    private Map data;
}