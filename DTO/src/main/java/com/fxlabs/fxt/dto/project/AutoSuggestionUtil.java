package com.fxlabs.fxt.dto.project;

import java.util.HashMap;

public class AutoSuggestionUtil {

    // Category -> Issue Description, Estimates, Suggestion

    private static final HashMap<String, AutoSuggestion> SUGESTION_CATEGORY_MAP = new HashMap<>();

    static {

        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.UnSecured.toString(), populateAutoSuggestDetails(TestSuiteCategory.UnSecured));
        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.DDOS.toString(), populateAutoSuggestDetails(TestSuiteCategory.DDOS));
        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.XSS_Injection.toString(), populateAutoSuggestDetails(TestSuiteCategory.XSS_Injection));
        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.SQL_Injection.toString(), populateAutoSuggestDetails(TestSuiteCategory.SQL_Injection));
        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.Log_Forging.toString(), populateAutoSuggestDetails(TestSuiteCategory.Log_Forging));
        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.RBAC.toString(), populateAutoSuggestDetails(TestSuiteCategory.RBAC));
        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.Negative.toString(), populateAutoSuggestDetails(TestSuiteCategory.Negative));
        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.Functional.toString(), populateAutoSuggestDetails(TestSuiteCategory.Functional));
        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.SimpleGET.toString(), populateAutoSuggestDetails(TestSuiteCategory.SimpleGET));
        SUGESTION_CATEGORY_MAP.put(TestSuiteCategory.SLA.toString(), populateAutoSuggestDetails(TestSuiteCategory.SLA));

    }

    private static AutoSuggestion populateAutoSuggestDetails(TestSuiteCategory category) {

        AutoSuggestion autoSuggestion = new AutoSuggestion();
        autoSuggestion.setCategory(category.toString());

        switch (category) {
            case UnSecured:
                autoSuggestion.setIssueDesc("This endpoint is not secured. It is accessible by unauthorized users.");
                autoSuggestion.setEstimates("Less than 8 hours.");
                autoSuggestion.setSuggestion("Make this endpoint Secured.");
                break;
            case DDOS:
                autoSuggestion.setIssueDesc("The application is prone to DDoS attack. This endpoint allows requests that trigger huge processing.");
                autoSuggestion.setEstimates("Less than 8 hours.");
                autoSuggestion.setSuggestion("Add a Max limit on the data being requested.");
                break;
//            case XSS_Injection:
//                autoSuggestion.setIssueDesc("The end point is not Secured. It is accessible by unauthorized users.");
//                autoSuggestion.setEstimates("Less than 8 hours.");
//                autoSuggestion.setSuggestion("Make this endpoint Secured.");
//                break;
            case SQL_Injection:
                autoSuggestion.setIssueDesc("The application is prone to SQL Injection attack. It seems the input(s) to this endpoint are processed by DB directly.");
                autoSuggestion.setEstimates("Less than 8 hours.");
                autoSuggestion.setSuggestion("Process the input and filter malicious data before sending it to DB.");
                break;
//            case Log_Forging:
//                autoSuggestion.setIssueDesc("The end point is not Secured. It is accessible by unauthorized users.");
//                autoSuggestion.setEstimates("Less than 8 hours.");
//                autoSuggestion.setSuggestion("Make this endpoint Secured.");
//                break;
            case RBAC:
                autoSuggestion.setIssueDesc("The end point is not fully Secured. It is accessible by unauthorized users.");
                autoSuggestion.setEstimates("Less than 8 hours.");
                autoSuggestion.setSuggestion("Make sure this endpoint is accessible only y intended roles.");
                break;
            case Negative:
                autoSuggestion.setIssueDesc("The end point is processing negative data. It might result in improper behaviour of the application");
                autoSuggestion.setEstimates("Less than 8 hours.");
                autoSuggestion.setSuggestion("Add data validation on all inputs");
                break;
//            case Functional:
//                autoSuggestion.setIssueDesc("The end point is not Secured. It is accessible by unauthorized users.");
//                autoSuggestion.setEstimates("Less than 8 hours.");
//                autoSuggestion.setSuggestion("Make this endpoint Secured.");
//                break;
            case SimpleGET:
                autoSuggestion.setIssueDesc("GET call on this endpoint is not working.");
                autoSuggestion.setEstimates("Less than 8 hours.");
                autoSuggestion.setSuggestion("Make sure the endpoint is correct.");
                break;
            case SLA:
                autoSuggestion.setIssueDesc("This call is taking more time to process than the expected SLA.");
                autoSuggestion.setEstimates("Less than 8 hours.");
                autoSuggestion.setSuggestion("Improve the performance of this call.");
                break;
            default:
                break;
        }
        return autoSuggestion;
    }


    public static AutoSuggestion getAutoSuggestion(String category) {

        return SUGESTION_CATEGORY_MAP.get(category);
    }
}
