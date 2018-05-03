package com.fxlabs.fxt.dao.entity.project;

/**
 * @author Mohammed Luqman Shareef
 */
public enum TestSuiteCategory {

    BUG("Bug"),
    USE_CASE("UseCase"),
    POSITIVE("Positive"),
    NEGATIVE("Negative"),
    WEAK_PASSWORD ("Weak_Password"),
    SECURITY_UNSECURED("Security_UnSecured"),
    SECURITY_DDOS("Security_DDOS"),
    SECURITY_XSS("Security_XSS"),
    SECURITY_SQL_INJECTION("Security_SQLInjection");

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
