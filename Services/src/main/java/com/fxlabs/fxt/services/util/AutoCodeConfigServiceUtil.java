package com.fxlabs.fxt.services.util;

import com.fxlabs.fxt.dto.project.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        autoCodeGenerators.add(getSql_injection());
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

    private static AutoCodeGenerator getCreate() {

        AutoCodeGenerator create = new AutoCodeGenerator();
        create.setInactive(false);
        create.setSeverity(TestSuiteSeverity.Critical);

        create.setType("create");

        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        create.setAssertions(assertions);

        return create;
    }

    private static AutoCodeGenerator getEmpty_value() {

        AutoCodeGenerator empty_value = new AutoCodeGenerator();
        empty_value.setInactive(false);
        empty_value.setSeverity(TestSuiteSeverity.Critical);

        empty_value.setType("empty_value");

        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        empty_value.setAssertions(assertions);
        return empty_value;
    }

    private static AutoCodeGenerator getNull_value() {
        AutoCodeGenerator null_value = new AutoCodeGenerator();
        null_value.setInactive(false);

        null_value.setSeverity(TestSuiteSeverity.Critical);
        null_value.setType("anonymous_invalid");

        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        null_value.setAssertions(assertions);

        return null_value;
    }

    private static AutoCodeGenerator getSpecial_chars() {
        AutoCodeGenerator special_chars = new AutoCodeGenerator();
        special_chars.setInactive(false);
        special_chars.setSeverity(TestSuiteSeverity.Critical);

        special_chars.setType("special_chars ");

        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        special_chars.setAssertions(assertions);
        return special_chars;
    }

    private static AutoCodeGenerator getInvalid_datatype() {
        AutoCodeGenerator invalid_datatype = new AutoCodeGenerator();
        invalid_datatype.setInactive(false);

        invalid_datatype.setSeverity(TestSuiteSeverity.Critical);
        invalid_datatype.setType("invalid_datatype");

        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        invalid_datatype.setAssertions(assertions);

        return invalid_datatype;
    }

    private static AutoCodeGenerator getRbac() {
        AutoCodeGenerator rbac = new AutoCodeGenerator();
        rbac.setInactive(false);

        rbac.setSeverity(TestSuiteSeverity.Critical);
        rbac.setType("rbac");

        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        rbac.setAssertions(assertions);

        return rbac;
    }

    private static AutoCodeGenerator getLog_forging() {
        AutoCodeGenerator log_Forging = new AutoCodeGenerator();
        log_Forging.setInactive(false);
        log_Forging.setSeverity(TestSuiteSeverity.Critical);

        log_Forging.setType("log_Forging");

        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        log_Forging.setAssertions(assertions);

        return log_Forging;
    }

    private static AutoCodeGenerator getSql_injection_timebound() {
        AutoCodeGenerator sql_injection_timebound = new AutoCodeGenerator();

        sql_injection_timebound.setInactive(false);
        sql_injection_timebound.setSeverity(TestSuiteSeverity.Critical);
        sql_injection_timebound.setType("sql_injection_timebound");

        List<String> assertions = new ArrayList<>();
        assertions.add("@ResponseTime >= 5000");
        sql_injection_timebound.setAssertions(assertions);


        Database db = new Database();
        db.setName("MySQL");
        db.setVersion("5.7");
        sql_injection_timebound.setDatabase(db);
        return sql_injection_timebound;
    }

    private static AutoCodeGenerator getSql_injection() {

        AutoCodeGenerator sql_injection = new AutoCodeGenerator();
        sql_injection.setInactive(false);

        sql_injection.setSeverity(TestSuiteSeverity.Critical);
        sql_injection.setType("sql_injection");

        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        sql_injection.setAssertions(assertions);

        Database db = new Database();
        db.setName("MySQL");
        db.setVersion("5.7");
        sql_injection.setDatabase(db);
        return sql_injection;
    }

    private static AutoCodeGenerator getXss_injection() {
        AutoCodeGenerator XSS_Injection = new AutoCodeGenerator();
        XSS_Injection.setInactive(false);
        XSS_Injection.setSeverity(TestSuiteSeverity.Critical);
        XSS_Injection.setType("XSS_Injection");


        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        XSS_Injection.setAssertions(assertions);

        return XSS_Injection;
    }

    private static AutoCodeGenerator getDdos() {

        AutoCodeGenerator ddos = new AutoCodeGenerator();
        ddos.setInactive(false);

        ddos.setSeverity(TestSuiteSeverity.Critical);
        ddos.setType("ddos");

        List<String> assertions = new ArrayList<>();
        assertions.add("@StatusCode != 200");
        ddos.setAssertions(assertions);

        return ddos;
    }

    private static AutoCodeGenerator getAuth_invalid() {
        AutoCodeGenerator auth_invalid = new AutoCodeGenerator();
        auth_invalid.setInactive(false);

        auth_invalid.setSeverity(TestSuiteSeverity.Critical);
        auth_invalid.setType("auth_invalid");

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
