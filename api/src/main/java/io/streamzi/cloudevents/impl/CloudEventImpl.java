package io.streamzi.cloudevents.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.streamzi.cloudevents.CloudEvent;

import java.io.Serializable;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class CloudEventImpl<T> implements CloudEvent<T>, Serializable {

    public static final String EVENT_TYPE_KEY = "eventType";
    public static final String CLOUD_EVENTS_VERSION_KEY = "cloudEventsVersion";
    public static final String SOURCE_KEY = "source";
    public static final String EVENT_ID_KEY = "eventID";
    public static final String EVENT_TYPE_VERSION_KEY = "eventTypeVersion";
    public static final String EVENT_TIME_KEY = "eventTime";
    public static final String SCHEMA_URL_KEY = "schemaURL";
    public static final String CONTENT_TYPE_KEY = "contentType";
    public static final String EXTENSIONS_KEY = "extensions";
    public static final String DATA_KEY = "data";

    private static final long serialVersionUID = 2L;

    @JsonIgnore
    public Map<String, ? super Object> getAttributes() {
        return attributes;
    }

    private final Map<String, ? super Object> attributes = new HashMap();

    public CloudEventImpl(final String eventType, final String cloudEventsVersion, final URI source, final String eventID, final String eventTypeVersion, final ZonedDateTime eventTime, final URI schemaURL, final String contentType, final Map extensions, final T data) {
        attributes.put(EVENT_TYPE_KEY, eventType);
        attributes.put(CLOUD_EVENTS_VERSION_KEY, cloudEventsVersion);
        attributes.put(SOURCE_KEY, source);
        attributes.put(EVENT_ID_KEY, eventID);
        attributes.put(EVENT_TYPE_VERSION_KEY, eventTypeVersion);
        attributes.put(EVENT_TIME_KEY, eventTime);
        attributes.put(SCHEMA_URL_KEY, schemaURL);

        if (extensions != null) {
            attributes.put(EXTENSIONS_KEY, extensions);
        } else {
            attributes.put(EXTENSIONS_KEY, new LinkedHashMap());
        }
        attributes.put(CONTENT_TYPE_KEY, contentType);
        attributes.put(DATA_KEY, data);

    }
    CloudEventImpl() {

    }

    @Override
    public String getEventType() {
        return (String) attributes.get(EVENT_TYPE_KEY);
    }

    @Override
    public String getCloudEventsVersion() {
        return (String) attributes.get(CLOUD_EVENTS_VERSION_KEY);
    }

    @Override
    public URI getSource() {
        return (URI) attributes.get(SOURCE_KEY);
    }

    @Override
    public String getEventID() {
        return (String) attributes.get(EVENT_ID_KEY);
    }

    @Override
    public Optional<String> getEventTypeVersion() {
        return Optional.ofNullable((String) attributes.get(EVENT_TYPE_VERSION_KEY));
    }

    @Override
    public Optional<ZonedDateTime> getEventTime() {
        return Optional.ofNullable((ZonedDateTime) attributes.get(EVENT_TIME_KEY));
    }

    @Override
    public Optional<URI> getSchemaURL() {
        return Optional.ofNullable((URI) attributes.get(SCHEMA_URL_KEY));
    }

    @Override
    public Optional<String> getContentType() {
        return Optional.ofNullable((String) attributes.get(CONTENT_TYPE_KEY));
    }

    @Override
    public Optional<Map> getExtensions() {
        return Optional.ofNullable((Map) attributes.get(EXTENSIONS_KEY));
    }

    @Override
    public Optional<T> getData() {
        return Optional.ofNullable((T) attributes.get(DATA_KEY));
    }


    // protected setters for key/value pairs in the Map,
    // used for JSON deserialization

    void setEventType(String eventType) {
        attributes.put(EVENT_TYPE_KEY, eventType);
    }

    void setCloudEventsVersion(String cloudEventsVersion) {
        attributes.put(CLOUD_EVENTS_VERSION_KEY, cloudEventsVersion);
    }

    void setSource(URI source) {
        attributes.put(SOURCE_KEY, source);
    }

    void setEventID(String eventID) {
        attributes.put(EVENT_ID_KEY, eventID);
    }

    void setEventTypeVersion(String eventTypeVersion) {
        attributes.put(EVENT_TYPE_VERSION_KEY, eventTypeVersion);
    }

    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    void setEventTime(ZonedDateTime eventTime) {
        attributes.put(EVENT_TIME_KEY, eventTime);
    }

    void setSchemaURL(URI schemaURL) {
        attributes.put(SCHEMA_URL_KEY, schemaURL);
    }

    void setContentType(String contentType) {
        attributes.put(CONTENT_TYPE_KEY, contentType);
    }

    void setExtensions(Map extensions) {
        if (extensions != null) {
            attributes.put(EXTENSIONS_KEY, extensions);
        } else {
            attributes.put(EXTENSIONS_KEY, new LinkedHashMap());
        }
    }

    public void setData(T data) {
        attributes.put(DATA_KEY, data);
    }

    @Override
    public String toString() {
        return "CloudEventImpl{" +
                attributes + '}';
    }
}
