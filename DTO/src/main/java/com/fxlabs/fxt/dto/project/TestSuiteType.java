package com.fxlabs.fxt.dto.project;

/**
 * @author Intesar Shannan Mohammed
 */
public enum TestSuiteType {

    SUITE("Suite"), ABSTRACT("Abstract");

    private final String type;

    /**
     * @param type
     */
    private TestSuiteType(final String type) {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return type;
    }
}
