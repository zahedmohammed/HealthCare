package com.fxlabs.fxt.dto.project;

/**
 * @author Mohammed Luqman Shareef
 */
public enum TestSuiteSeverity {

    CRITICAL("Critical"),
    MAJOR("Major"),
    MINOR("Minor"),
    TRIVIAL("Trivial");

    private final String severity;

    /**
     * @param severity
     */
    private TestSuiteSeverity(final String severity) {
        this.severity = severity;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return severity;
    }
}
