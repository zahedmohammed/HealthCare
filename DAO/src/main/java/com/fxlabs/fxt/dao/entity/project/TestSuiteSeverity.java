package com.fxlabs.fxt.dao.entity.project;

/**
 * @author Mohammed Luqman Shareef
 */
public enum TestSuiteSeverity {

    Critical("Critical"),
    Major("Major"),
    Minor("Minor"),
    Trivial("Trivial");

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
