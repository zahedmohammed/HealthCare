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
                System.out.println(this.config.getPropertyMapping());
            }
        }
    }

    public AutoCodeConfig getConfig() {
        return config;
    }

    public void resetConfig() {
        this.config = null;
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
        TYPE_CATEGORY_MAP.put("Log_Forging", TestSuiteCategory.Log_Forging);
        TYPE_CATEGORY_MAP.put("invalid_datatype", TestSuiteCategory.Negative);
        TYPE_CATEGORY_MAP.put("special_chars", TestSuiteCategory.Negative);
        TYPE_CATEGORY_MAP.put("null_value", TestSuiteCategory.Negative);
        TYPE_CATEGORY_MAP.put("empty_value", TestSuiteCategory.Negative);
        TYPE_CATEGORY_MAP.put("create", TestSuiteCategory.Functional);

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

        if (this.config == null) return DEFAULT_ASSERTIONS;

        String version = null;
        if (!CollectionUtils.isEmpty(this.config.getTestSuites())) {
            for (TestSuite ts : this.config.getTestSuites()) {
                if (StringUtils.equalsIgnoreCase(ts.getType(), type)) {
                    if (CollectionUtils.isEmpty(ts.getAssertions()) ||
                            ts.getAssertions().get(0) == null ||
                            ts.getAssertions().get(0).equalsIgnoreCase("null")) {
                        return DEFAULT_ASSERTIONS;
                    } else {
                        return ts.getAssertions();
                    }
                }
            }
        }
        return DEFAULT_ASSERTIONS;
    }

    public TestSuiteSeverity getTestSuiteSeverity(String type) {

        TestSuiteSeverity severity = DEFAULT_SEVERITY;
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
        return TYPE_CATEGORY_MAP.get(type) != null ? TYPE_CATEGORY_MAP.get(type) : DEFAULT_CATEGORY;
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

        boolean isPresent = false;
        if (ts != null && !CollectionUtils.isEmpty(ts.getMatches())) {
            isPresent = ts.getMatches().stream().filter(match ->
                    StringUtils.equalsIgnoreCase(match.getName(), name)).findFirst().isPresent();
        }

        return isPresent || DEFAULT_DDOS_PARAM_NAMES.stream().filter(s -> StringUtils.equalsIgnoreCase(s, name)).findFirst().isPresent();

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

    // PropertyMapping

    public String getPropertyMapping(String node, String property) {
        if (this.config.getPropertyMapping() == null) {
            return null;
        }

        // 1. look for node.property
        // 2. look for property

        String nodeKey = String.format("%s.%s", node, property);
        if (this.config.getPropertyMapping().containsKey(nodeKey)) {
            return this.config.getPropertyMapping().get(nodeKey);
        }

        if (this.config.getPropertyMapping().containsKey(property)) {
            return this.config.getPropertyMapping().get(property);
        }

        return null;
    }

}
