package net.wessendorf.ce;

public enum CloudEventVersion {

    ZERO_ONE("0.1");

    private final String cloudEventVersion;
    private CloudEventVersion(final String version) {
        cloudEventVersion = version;
    }

    public String getVersion() {
        return cloudEventVersion;
    }
}
