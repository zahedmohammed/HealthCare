package com.fxlabs.fxt.dao.entity.project;

/**
 * @author Mohammed Luqman Shareef
 */
public enum TestSuiteCategory {

    Bug("Bug"),
    Use_Case("Use_Case"),
    Positive("Positive"),
    Negative("Negative"),
    Weak_Password ("Weak_Password"),
    Security_UnSecured("Security_UnSecured"),
    Security_DDOS("Security_DDOS"),
    Security_XSS("Security_XSS"),
    Security_SQL_Injection("Security_SQL_Injection");

    private final String category;

    /**
     * @param category
     */
    private TestSuiteCategory(final String category) {
        this.category = category;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return category;
    }
}
