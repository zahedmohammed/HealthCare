package com.fxlabs.fxt.dao.entity.project;

/**
 * @author Intesar Shannan Mohammed
 */
public enum TestSuiteType {

    SUITE("Suite"), ABSTRACT("Abstract"), DATASET("Dataset"), CONSULTING_SERVICES("Consulting_Services"), AI_SKILLS("AI_Skills");

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
