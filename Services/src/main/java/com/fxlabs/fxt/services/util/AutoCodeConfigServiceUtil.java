package com.fxlabs.fxt.services.util;

import com.fxlabs.fxt.dto.project.*;

import java.util.*;

/**
 * @author Mohammed Shoukath Ali
 */
public class AutoCodeConfigServiceUtil {


    public static AutoCodeConfig getAutoCodeConfig(Project project) {

        AutoCodeConfig autoCodeConfig = new AutoCodeConfig();

        autoCodeConfig.setProject(project);
        autoCodeConfig.setGenPolicy(GenPolicy.Create);

        List<AutoCodeGenerator> autoCodeGenerators = new ArrayList<>();

        autoCodeGenerators.add(getAnonymous_invalid());
        autoCodeGenerators.add(getAuth_invalid());
        autoCodeGenerators.add(getDdos());
        autoCodeGenerators.add(getXss_injection());
       // autoCodeGenerators.add(getSql_injection());
        autoCodeGenerators.add(getSql_injection_timebound());
        autoCodeGenerators.add(getLog_forging());
        autoCodeGenerators.add(getRbac());
        autoCodeGenerators.add(getInvalid_datatype());
        autoCodeGenerators.add(getSpecial_chars());
        autoCodeGenerators.add(getNull_value());
        autoCodeGenerators.add(getEmpty_value());
        autoCodeGenerators.add(getCreate());
        autoCodeConfig.setGenerators(autoCodeGenerators);
        return autoCodeConfig;

    }

    public static ProjectImports getAutoCodeConfigImports(String projectId) {

        ProjectImports projectImports = new ProjectImports();
        projectImports.setProjectId(projectId);
        Map<String, String> importsMap = new HashMap<>();

        importsMap.put("@MySQLTimeboundSQLInjections", "FXLabs/Common/MySQL-TimeBound-SQL_Injection_Strings");
        importsMap.put("@PostgresTimeboundSQLInjections", "FXLabs/Common/PostGreSQL-TimeBound-SQL_Injection_Strings");
        importsMap.put("@SQLServerTimeboundSQLInjections", "FXLabs/Common/SQLServer-TimeBound-SQL_Injection_Strings");
        importsMap.put("@OracleTimeboundSQLInjections", "FXLabs/Common/MySQL-TimeBound-SQL_Injection_Strings");

        projectImports.setImports(importsMap);

        return projectImports;
    }

    private static AutoCodeGenerator getCreate() {

        AutoCodeGenerator create = new AutoCodeGenerator();
        create.setInactive(false);
        create.setSeverity(TestSuiteSeverity.Critical);

        create.setType("create");
        create.setDisplayHeaderLabel("Create");
        create.setDisplayHeaderDescription("Create test suite");
        create.setAssertionDescription("Successful test suite response code is 200. UnSuccessful test suite response code is 400, 403, 406 and 500.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        assertions.add("@StatusCode != 401");
        assertions.add("@StatusCode != 404");
        assertions.add("@StatusCode != 500");
        create.setAssertions(assertions);

        return create;
    }

    private static AutoCodeGenerator getEmpty_value() {

        AutoCodeGenerator empty_value = new AutoCodeGenerator();
        empty_value.setInactive(false);
        empty_value.setSeverity(TestSuiteSeverity.Critical);

        empty_value.setType("empty_value");

        empty_value.setDisplayHeaderLabel("Empty Value");
        empty_value.setDisplayHeaderDescription("Empty value is a negative test case to check how APIs behave when empty values are sent in a data type that API is not expecing.");
        empty_value.setAssertionDescription("Successful test suite response code is 400, 403, 406 and 500. UnSuccessful test suite response code is 200.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        assertions.add("@StatusCode != 401");
        assertions.add("@StatusCode != 404");
        assertions.add("@StatusCode != 500");
        empty_value.setAssertions(assertions);
        return empty_value;
    }

    private static AutoCodeGenerator getNull_value() {
        AutoCodeGenerator null_value = new AutoCodeGenerator();
        null_value.setInactive(false);

        null_value.setSeverity(TestSuiteSeverity.Critical);
        null_value.setType("null_value");

        null_value.setDisplayHeaderLabel("Null Value");
        null_value.setDisplayHeaderDescription("Null value is a negative test case to check how APIs behave when Null values are sent in a data type that API is not expecing.");
        null_value.setAssertionDescription("Successful test suite response code is 400, 403, 406 and 500. UnSuccessful test suite response code is 200.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        assertions.add("@StatusCode != 401");
        assertions.add("@StatusCode != 404");
        assertions.add("@StatusCode != 500");
        null_value.setAssertions(assertions);

        return null_value;
    }

    private static AutoCodeGenerator getSpecial_chars() {
        AutoCodeGenerator special_chars = new AutoCodeGenerator();
        special_chars.setInactive(false);
        special_chars.setSeverity(TestSuiteSeverity.Critical);

        special_chars.setType("special_chars ");
        special_chars.setDisplayHeaderLabel("Special Characters");
        special_chars.setDisplayHeaderDescription("Special Characters is a negative test case to check how APIs behave when special characters are sent in the data type that API is not expecing.");
        special_chars.setAssertionDescription("Successful test suite response code is 400, 403, 406 and 500. UnSuccessful test suite response code is 200.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        assertions.add("@StatusCode != 401");
        assertions.add("@StatusCode != 404");
        assertions.add("@StatusCode != 500");
        special_chars.setAssertions(assertions);
        return special_chars;
    }

    private static AutoCodeGenerator getInvalid_datatype() {
        AutoCodeGenerator invalid_datatype = new AutoCodeGenerator();
        invalid_datatype.setInactive(false);

        invalid_datatype.setSeverity(TestSuiteSeverity.Critical);
        invalid_datatype.setType("invalid_datatype");

        invalid_datatype.setDisplayHeaderLabel("Invalid Data Type");
        invalid_datatype.setDisplayHeaderDescription("Invalid Data Type is a negative test case to check how APIs behave when a data type is sent other than the one API is expecting.");
        invalid_datatype.setAssertionDescription("Successful test suite response code is 400, 403, 406 and 500. UnSuccessful test suite response code is 200.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        assertions.add("@StatusCode != 401");
        assertions.add("@StatusCode != 404");
        assertions.add("@StatusCode != 500");
        invalid_datatype.setAssertions(assertions);

        return invalid_datatype;
    }

    private static AutoCodeGenerator getRbac() {
        AutoCodeGenerator rbac = new AutoCodeGenerator();
        rbac.setInactive(false);

        rbac.setSeverity(TestSuiteSeverity.Critical);
        rbac.setType("rbac");
        rbac.setDisplayHeaderLabel("Role Based Access Control");
        rbac.setDisplayHeaderDescription("RBAC Test suite is a way to test security that checks Roles access information they are authorized to and are not accessing additional information that is not relevant to them.");
        rbac.setAssertionDescription("Successful test suite response codes are 200. UnSuccessful test suite response codes are 401, 403.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode == 403");
        rbac.setAssertions(assertions);

        List<AutoCodeGeneratorMatches > matchesList = new ArrayList<>();

        AutoCodeGeneratorMatches admin = new AutoCodeGeneratorMatches();
        admin.setName("Admin access");
        admin.setPathPatterns("/api/v1/orgs/**, /api/v1/accounts/**");
        admin.setMethods("Post, Put, Delete");
        admin.setDenyRoles("Writer, Reader");
        matchesList.add(admin);

        AutoCodeGeneratorMatches writer = new AutoCodeGeneratorMatches();
        writer.setName("Writer access");
        writer.setPathPatterns("/api/v1/projects/**, /api/v1/regions/**");
        writer.setMethods("Post, Put, Delete");
        writer.setDenyRoles("Reader");
        matchesList.add(writer);

        AutoCodeGeneratorMatches collaborator = new AutoCodeGeneratorMatches();
        collaborator.setName("Collaborator access");
        collaborator.setPathPatterns("/api/v1/projects/**");
        collaborator.setMethods("Post, Delete");
        collaborator.setDenyRoles("Collaborator");
        matchesList.add(collaborator);

        AutoCodeGeneratorMatches reader = new AutoCodeGeneratorMatches();
        reader.setName("Reader access");
        reader.setPathPatterns("/api/v1/**");
        reader.setMethods("Get, Post, Put, Delete");
        reader.setDenyRoles("Other");
        matchesList.add(reader);

        rbac.setMatches(matchesList);

        return rbac;
    }

    private static AutoCodeGenerator getLog_forging() {
        AutoCodeGenerator log_Forging = new AutoCodeGenerator();
        log_Forging.setInactive(false);
        log_Forging.setSeverity(TestSuiteSeverity.Critical);

        log_Forging.setType("log_Forging");

        log_Forging.setDisplayHeaderLabel("Log Forging");
        log_Forging.setDisplayHeaderDescription("Log Forging is writing unvalidated user input to log files to allow an attacker to forge log entries or inject malicious content into the logs.");
        log_Forging.setAssertionDescription("Successful test suite response code is 200. UnSuccessful test suite response code is 400, 401 and 500.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        assertions.add("@StatusCode != 401");
        assertions.add("@StatusCode != 404");
        assertions.add("@StatusCode != 500");
        log_Forging.setAssertions(assertions);

        return log_Forging;
    }

    private static AutoCodeGenerator getSql_injection_timebound() {
        AutoCodeGenerator sql_injection_timebound = new AutoCodeGenerator();

        sql_injection_timebound.setInactive(false);
        sql_injection_timebound.setSeverity(TestSuiteSeverity.Critical);
        sql_injection_timebound.setType("sql_injection");
        sql_injection_timebound.setDisplayHeaderLabel("SQL Injection");
        sql_injection_timebound.setDisplayHeaderDescription("SQL injection attacks are a type of injection attack, in which SQL commands are injected into data-plane input in order to affect the execution of predefined SQL commands.");
        sql_injection_timebound.setAssertionDescription("Successful test suite response codes are 200 or 408. UnSuccessful test suite response codes are 400, 404, 500.");

        List<String> assertions = new ArrayList<>();
        assertions.add("@ResponseTime >= 5000");
        assertions.add("@StatusCode != 401");
        assertions.add("@StatusCode != 404");
        assertions.add("@StatusCode != 500");
        sql_injection_timebound.setAssertions(assertions);


        Database db = new Database();
        db.setName("MySQL");
        db.setVersion("5.7");
        sql_injection_timebound.setDatabase(db);

        AutoCodeGeneratorMatches matches = new AutoCodeGeneratorMatches();
        matches.setBodyProperties("id,name");
        matches.setResourceSamples("User-Create");

        List<AutoCodeGeneratorMatches > matchesList = new ArrayList<>();
        sql_injection_timebound.setMatches(matchesList);

        return sql_injection_timebound;
    }

//    private static AutoCodeGenerator getSql_injection() {
//
//        AutoCodeGenerator sql_injection = new AutoCodeGenerator();
//        sql_injection.setInactive(false);
//
//        sql_injection.setSeverity(TestSuiteSeverity.Critical);
//        sql_injection.setType("sql_injection");
//
//        List<String> assertions = new ArrayList<>();
//        assertions.add("@StatusCode != 200");
//        assertions.add("@StatusCode != 401");
//        assertions.add("@StatusCode != 404");
//        assertions.add("@StatusCode != 500");
//        sql_injection.setAssertions(assertions);
//
//        Database db = new Database();
//        db.setName("MySQL");
//        db.setVersion("5.7");
//        sql_injection.setDatabase(db);
//        return sql_injection;
//    }

    private static AutoCodeGenerator getXss_injection() {
        AutoCodeGenerator XSS_Injection = new AutoCodeGenerator();
        XSS_Injection.setInactive(false);
        XSS_Injection.setSeverity(TestSuiteSeverity.Critical);
        XSS_Injection.setType("XSS_Injection");
        XSS_Injection.setDisplayHeaderLabel("Cross Site Scripting(XSS) Injection");
        XSS_Injection.setDisplayHeaderDescription("Cross-site scripting (XSS) is a type of injection security attack in which an attacker injects data, such as a malicious script, into content from otherwise trusted websites.");
        XSS_Injection.setAssertionDescription("Successful test suite attack response code is 200. UnSuccessful test suite response code is 400, 401 and 500.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        assertions.add("@StatusCode != 401");
        assertions.add("@StatusCode != 404");
        assertions.add("@StatusCode != 500");
        XSS_Injection.setAssertions(assertions);



        return XSS_Injection;
    }

    private static AutoCodeGenerator getDdos() {

        AutoCodeGenerator ddos = new AutoCodeGenerator();
        ddos.setInactive(false);

        ddos.setSeverity(TestSuiteSeverity.Critical);
        ddos.setType("ddos");

        ddos.setDisplayHeaderLabel("Distributed Denial of Service");
        ddos.setDisplayHeaderDescription("A Distributed Denial of Service (DDoS) attack is an attempt to make an online service unavailable by overwhelming it with traffic from multiple sources.");
        ddos.setAssertionDescription("Successful test suite response code is 401, 404, 500 or 503. UnSuccessful test suite response code is 200.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        assertions.add("@StatusCode != 401");
        assertions.add("@StatusCode != 404");
        assertions.add("@StatusCode != 500");
        ddos.setAssertions(assertions);

        AutoCodeGeneratorMatches match = new AutoCodeGeneratorMatches();
        match.setName("pageSize, page_size, maxResults, max_results");
        ddos.setMatches(Arrays.asList(match));

        return ddos;
    }

    private static AutoCodeGenerator getAuth_invalid() {
        AutoCodeGenerator auth_invalid = new AutoCodeGenerator();
        auth_invalid.setInactive(false);

        auth_invalid.setSeverity(TestSuiteSeverity.Critical);
        auth_invalid.setType("auth_invalid");

        auth_invalid.setDisplayHeaderLabel("Authorization Invalid");
        auth_invalid.setDisplayHeaderDescription("Authorization Invalid is a test suite to check APIs can be invoked by UnAuthorized users.");
        auth_invalid.setAssertionDescription("Successful test suite response code is 401, 403. UnSuccessful test suite response code is 200.");



        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        auth_invalid.setAssertions(assertions);

        return auth_invalid;
    }

    private static AutoCodeGenerator getAnonymous_invalid() {
        AutoCodeGenerator anonymous_invalid = new AutoCodeGenerator();
        anonymous_invalid.setInactive(false);

        anonymous_invalid.setSeverity(TestSuiteSeverity.Critical);
        anonymous_invalid.setType("anonymous_invalid");

        anonymous_invalid.setDisplayHeaderLabel("Anonymous Invalid");
        anonymous_invalid.setDisplayHeaderDescription("Anonymous Invalid is a test suite to check if Secure APIs can be invoked by anonymous users.");
        anonymous_invalid.setAssertionDescription("Successful test suite response code is 401, 403. UnSuccessful test suite response code is 200.");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");

        anonymous_invalid.setAssertions(assertions);
        return anonymous_invalid;
    }

    private static final List<String> DEFAULT_ASSERTIONS = new ArrayList<>();
    private static final TestSuiteCategory DEFAULT_CATEGORY = TestSuiteCategory.Functional;
    private static final TestSuiteSeverity DEFAULT_SEVERITY = TestSuiteSeverity.Major;
    private static final boolean DEFAULT_INACTIVE = false;

    private static final Map<String, TestSuiteCategory> TYPE_CATEGORY_MAP = new HashMap<>();

    static {
        DEFAULT_ASSERTIONS.add("@StatusCode != 200");

        TYPE_CATEGORY_MAP.put("anonymous_invalid", TestSuiteCategory.UnSecured);
        TYPE_CATEGORY_MAP.put("auth_invalid", TestSuiteCategory.UnSecured);
        TYPE_CATEGORY_MAP.put("DDOS", TestSuiteCategory.DDOS);
        TYPE_CATEGORY_MAP.put("XSS_Injection", TestSuiteCategory.XSS_Injection);
        TYPE_CATEGORY_MAP.put("sql_injection", TestSuiteCategory.SQL_Injection);
        TYPE_CATEGORY_MAP.put("sql_injection_timebound", TestSuiteCategory.SQL_Injection);
        TYPE_CATEGORY_MAP.put("Log_Forging", TestSuiteCategory.Log_Forging);
        TYPE_CATEGORY_MAP.put("rbac", TestSuiteCategory.RBAC);
        TYPE_CATEGORY_MAP.put("invalid_datatype", TestSuiteCategory.Negative);
        TYPE_CATEGORY_MAP.put("special_chars", TestSuiteCategory.Negative);
        TYPE_CATEGORY_MAP.put("null_value", TestSuiteCategory.Negative);
        TYPE_CATEGORY_MAP.put("empty_value", TestSuiteCategory.Negative);
        TYPE_CATEGORY_MAP.put("create", TestSuiteCategory.Functional);

    }


}
