package com.fxlabs.fxt.codegen.generators.utils;

import com.fxlabs.fxt.codegen.code.AutoCodeConfig;
import com.fxlabs.fxt.codegen.code.Database;
import com.fxlabs.fxt.codegen.code.Match;
import com.fxlabs.fxt.codegen.code.TestSuite;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
import io.swagger.models.HttpMethod;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author Mohammed Luqman Shareef
 * @author Intesar Shannan Mohammed
 */
@Component
public class AutoCodeConfigUtil {

    private AutoCodeConfig config;

    public void setConfig(AutoCodeConfig config) {
        if (this.config == null) {
            this.config = config;
            if (this.config.getTestSuites() != null) {
                this.config.getTestSuites().forEach(System.out::println);
                System.out.println("");
            }
        }
    }

    public AutoCodeConfig getConfig() {
        return config;
    }

    public void resetConfig() {
        this.config = null;
    }

    private static List<String> defaultAssertions = new ArrayList<>();
    private static TestSuiteCategory defaultCategory = TestSuiteCategory.Functional;
    private static TestSuiteSeverity defaultSeverity = TestSuiteSeverity.Major;
    private static boolean DEFAULT_INACTIVE = true;

    private static Map<String, TestSuiteCategory> typeCategoryMap = new HashMap<>();

    static {
        defaultAssertions.add("@StatusCode != 200");

        typeCategoryMap.put("anonymous_invalid", TestSuiteCategory.UnSecured);
        typeCategoryMap.put("auth_invalid", TestSuiteCategory.UnSecured);
        typeCategoryMap.put("DDOS", TestSuiteCategory.DDOS);
        typeCategoryMap.put("XSS_Injection", TestSuiteCategory.XSS_Injection);
        typeCategoryMap.put("sql_injection", TestSuiteCategory.SQL_Injection);
        typeCategoryMap.put("Log_Forging", TestSuiteCategory.Log_Forging);
        typeCategoryMap.put("invalid_datatype", TestSuiteCategory.Negative);
        typeCategoryMap.put("special_chars", TestSuiteCategory.Negative);
        typeCategoryMap.put("null_value", TestSuiteCategory.Negative);
        typeCategoryMap.put("empty_value", TestSuiteCategory.Negative);
        typeCategoryMap.put("create", TestSuiteCategory.Functional);

    }

    public boolean isDB(String dbName) {

        // For now, returning true for every DB (if config file is not present) so that TestSuites for all DBs are generated.
        //TODO: If we make AutoCodeConfig.yaml mandatory with entries of DB, we need to return false here.
        if (this.config == null) return true;

        boolean isDBGivenName = false;
        if (!CollectionUtils.isEmpty(this.config.getDatabases())) {
            for (Database db : this.config.getDatabases()) {
                if (StringUtils.equalsIgnoreCase(db.getName(), dbName)) {
                    if (!db.isInactive())
                        return true;
                }
            }
        }
        return false;
    }

    public String getDBVersion(String dbName) {

        if (this.config == null) return null;

        String version = null;
        if (!CollectionUtils.isEmpty(this.config.getDatabases())) {
            for (Database db : this.config.getDatabases()) {
                if (StringUtils.equalsIgnoreCase(db.getName(), dbName)) {
                    version = db.getVersion();
                }
            }
        }
        return version;
    }

    public List<String> getLogForgingPatterns() {
        if (this.config == null) return null;

        return this.config.getLogForgingPatterns();
    }

    public List<String> getAssertions(String type) {

        if (this.config == null) return defaultAssertions;

        String version = null;
        if (!CollectionUtils.isEmpty(this.config.getTestSuites())) {
            for (TestSuite ts : this.config.getTestSuites()) {
                if (StringUtils.equalsIgnoreCase(ts.getType(), type)) {
                    if (CollectionUtils.isEmpty(ts.getAssertions()) ||
                            ts.getAssertions().get(0) == null ||
                            ts.getAssertions().get(0).equalsIgnoreCase("null")) {
                        return defaultAssertions;
                    } else {
                        return ts.getAssertions();
                    }
                }
            }
        }
        return defaultAssertions;
    }

    public TestSuiteSeverity getTestSuiteSeverity(String type) {

        TestSuiteSeverity severity = defaultSeverity;
        if (this.config == null) return severity;

        String version = null;
        if (!CollectionUtils.isEmpty(this.config.getTestSuites())) {
            for (TestSuite ts : this.config.getTestSuites()) {
                if (StringUtils.equalsIgnoreCase(ts.getType(), type)) {
                    try {
                        severity = TestSuiteSeverity.valueOf(ts.getSeverity());
                    } catch (Exception ex) {
                    }
                    break;
                }
            }
        }
        return severity;
    }

    public TestSuiteCategory getTestSuiteCategory(String type) {
        return typeCategoryMap.get(type) != null ? typeCategoryMap.get(type) : defaultCategory;
    }

    public String getTestSuitePostfix(String type) {

        if (this.config == null) return type;

        String version = null;
        if (!CollectionUtils.isEmpty(this.config.getTestSuites())) {
            for (TestSuite ts : this.config.getTestSuites()) {
                if (StringUtils.equalsIgnoreCase(ts.getType(), type)) {
                    return ts.getPostfix() != null ? ts.getPostfix() : ts.getType();
                }
            }
        }
        return type;
    }

    public boolean isInactive(String type) {

        TestSuite ts = get(type);
        if (ts != null) {
            //System.out.println(" ########## " + type + " " + ts.isInactive());
            return ts.isInactive();
        }
        //System.out.println(" ########## " + type + "  " + DEFAULT_INACTIVE);
        return DEFAULT_INACTIVE;
    }

    // DDOS support
    public boolean isDDOSSupportedMethod(io.swagger.models.HttpMethod method) {
        if (method == null) {
            return false;
        }
        // Support both Get & Post
        return method == HttpMethod.GET || method == HttpMethod.POST;
    }

    public boolean isDDOSSupportedParam(Parameter parameter) {
        if (parameter == null) {
            return false;
        }
        // Query parameter is standard
        return parameter instanceof QueryParameter;
    }

    public boolean isDDOSSupportedType(Parameter parameter) {

        if (parameter == null) {
            return false;
        }

        if (!(parameter instanceof QueryParameter)) {
            return false;
        }

        QueryParameter queryParam = (QueryParameter) parameter;

        // boolean, array and object can be safely ignored.
        return "integer".equals(queryParam.getType()) || "string".equals(queryParam.getType());
    }

    // TODO Default names for pageSize in Java Spring, Jersey, Python, Ruby, .Net etc.
    private static final Collection<String> DEFAULT_DDOS_PARAM_NAMES = Arrays.asList("pageSize", "page_size", "maxResults", "max_results");

    public boolean isDDOSSupportedName(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }

        TestSuite ts = get("DDOS");

        if (ts != null && !CollectionUtils.isEmpty(ts.getMatches())) {
            return ts.getMatches().stream().anyMatch(match ->
                    StringUtils.equals(match.getName(), name)
            );
        }

        return DEFAULT_DDOS_PARAM_NAMES.stream().anyMatch(s -> StringUtils.equalsAnyIgnoreCase(s, name));

    }

    private static final int DEFAULT_DDOS_VALUE = 1001;

    public int getDDOSSupportedValue(String name) {
        if (StringUtils.isEmpty(name)) {
            return DEFAULT_DDOS_VALUE;
        }
        TestSuite ts = get("DDOS");

        if (ts == null || CollectionUtils.isEmpty(ts.getMatches())) {
            return DEFAULT_DDOS_VALUE;
        }

        Optional<Match> matchOptional = ts.getMatches().stream().filter(match ->
                StringUtils.equals(match.getName(), name)).findFirst();

        if (matchOptional.isPresent()) {
            if (!StringUtils.isEmpty(matchOptional.get().getValue())) {
                try {
                    int val = Integer.parseInt(matchOptional.get().getValue());
                    return val;
                } catch (NumberFormatException nfe) {
                    // TODO Logger
                }
            }
        }
        return DEFAULT_DDOS_VALUE;
    }


    // type filter
    private TestSuite get(String type) {

        if (this.config.getTestSuites() == null || CollectionUtils.isEmpty(this.config.getTestSuites())) {
            return null;
        }

        return this.config.getTestSuites().stream().filter(testSuite -> StringUtils.equals(testSuite.getType(), type)).findFirst().orElse(null);

    }

}
