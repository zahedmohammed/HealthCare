package com.fxlabs.fxt.dto.project;

/**
 * @author Mohammed Luqman Shareef
 */
public enum TestSuiteCategory {

    Bug("Bug"),
//    Use_Case("Use_Case"),
    Functional("Functional"),
//    Positive("Positive"),
    Negative("Negative"),
//    Weak_Password ("Weak_Password"),
//    Security_UnSecured("Security_UnSecured"),
//    Security_DDOS("Security_DDOS"),
//    Security_XSS("Security_XSS"),
//    Security_SQL_Injection("Security_SQL_Injection"),

    UnSecured("UnSecured"),
    DDOS("DDOS"),
    XSS_Injection("XSS_Injection"),
    SQL_Injection("SQL_Injection"),
    Log_Forging("Log_Forging"),
    RBAC("RBAC"),
    HelloWorld("HelloWorld"),
    SimpleGET("SimpleGET"),
    SLA("SLA");

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
