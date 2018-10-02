package com.fxlabs.fxt.codegen.generators.utils;

import com.fxlabs.fxt.codegen.code.AutoCodeConfig;
import com.fxlabs.fxt.codegen.code.Generator;
import com.fxlabs.fxt.codegen.code.Match;
import com.fxlabs.fxt.dto.project.ResourceSample;
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
        if (config == null) {
            return;
        }
        if (this.config == null) {
            this.config = config;
//            if (this.config.getTestSuites() != null) {
//                this.config.getTestSuites().forEach(System.out::println);
//                System.out.println("");
//                System.out.println(this.config.getPropertyMapping());
//            }
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
    private static final boolean DEFAULT_INACTIVE = true;

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
        TYPE_CATEGORY_MAP.put("no_params", TestSuiteCategory.SimpleGET);
        TYPE_CATEGORY_MAP.put("sla", TestSuiteCategory.SLA);

    }


    public static List<String> getTypes(List<String> categories) {
        List<String> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(categories)) return list;

        List<TestSuiteCategory> categories_ = new ArrayList<>();
        for (String cat : categories) {
            try {
                categories_.add(TestSuiteCategory.valueOf(cat));
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
        }
        return getTypesForCategories(categories_);
    }


    public static final List<String> getTypesForCategories(List<TestSuiteCategory> categories) {
        List<String> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(categories)) return list;

        Iterator<String> itr = TYPE_CATEGORY_MAP.keySet().iterator();

        while (itr.hasNext()) {
            String type = itr.next();
            TestSuiteCategory category = TYPE_CATEGORY_MAP.get(type);
            if (category != null && categories.contains(category)) {
                list.add(type);
            }
        }
        return list;
    }

//    public boolean isDB(String dbName) {
//
//        // For now, returning true for every DB (if config file is not present) so that TestSuites for all DBs are generated.
//        //TODO: If we make AutoCodeConfig.yaml mandatory with entries of DB, we need to return false here.
//        if (this.config == null) return true;
//
//        boolean isDBGivenName = false;
//        if (!CollectionUtils.isEmpty(this.config.getGenerators()) &&
//                !CollectionUtils.isEmpty(this.config.getDatabases())) {
//            for (Database db : this.config.getDatabases()) {
//                if (StringUtils.equalsIgnoreCase(db.getName(), dbName)) {
//                    if (!db.isInactive())
//                        return true;
//                }
//            }
//        }
//        return false;
//    }


    public boolean isDB(String generatorType, String dbName) {

        if (this.config == null) return false;

        boolean isDBGivenName = false;
        if (!CollectionUtils.isEmpty(this.config.getGenerators())) {
            for (Generator gen : this.config.getGenerators()) {
                if (gen.getDatabase() != null && StringUtils.equalsIgnoreCase(gen.getDatabase().getName(), dbName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getResourceSample(String generatorType, String resourceName) {
        if (this.config == null) return null;
        boolean isDBGivenName = false;
        if (!CollectionUtils.isEmpty(this.config.getGenerators())) {
            for (Generator gen : this.config.getGenerators()) {
                if (!CollectionUtils.isEmpty(gen.getMatches())) {
                    Match match = gen.getMatches().get(0);
                    if (match != null) {
                        if (StringUtils.containsIgnoreCase(match.getResourceSamples(), resourceName)) {
                            ResourceSample resSample = getResourceSamples(resourceName);
                            return resSample == null ? null : resSample.getSample();
                        }
                    }

                }
            }
        }
        return null;
    }

    public String getQueryParams(String generatorType) {
        if (this.config == null) return null;
        boolean isDBGivenName = false;
        if (!CollectionUtils.isEmpty(this.config.getGenerators())) {
            for (Generator gen : this.config.getGenerators()) {
                if (!CollectionUtils.isEmpty(gen.getMatches())) {
                    Match match = gen.getMatches().get(0);
                    if (match != null) {
                        return match.getQueryParams();
                    }

                }
            }
        }
        return null;
    }

    public String getBodyProperties(String generatorType) {
        if (this.config == null) return null;
        boolean isDBGivenName = false;
        if (!CollectionUtils.isEmpty(this.config.getGenerators())) {
            for (Generator gen : this.config.getGenerators()) {
                if (!CollectionUtils.isEmpty(gen.getMatches())) {
                    Match match = gen.getMatches().get(0);
                    if (match != null) {
                        return match.getBodyProperties();
                    }

                }
            }
        }
        return null;
    }

    public String getResourceSampleMappings(String generatorType) {
        if (this.config == null) return null;
        boolean isDBGivenName = false;
        if (!CollectionUtils.isEmpty(this.config.getGenerators())) {
            for (Generator gen : this.config.getGenerators()) {
                if (!CollectionUtils.isEmpty(gen.getMatches())) {
                    Match match = gen.getMatches().get(0);
                    if (match != null) {
                        return match.getResourceSamples();
                    }

                }
            }
        }
        return null;
    }

    public String getDBVersion(String generatorType, String dbName) {
        if (this.config == null) return null;
        boolean isDBGivenName = false;
        if (!CollectionUtils.isEmpty(this.config.getGenerators())) {
            for (Generator gen : this.config.getGenerators()) {
                if (gen.getDatabase() != null && StringUtils.equalsIgnoreCase(gen.getDatabase().getName(), dbName)) {
                    return gen.getDatabase().getVersion();
                }
            }
        }
        return null;
    }

    public List<String> getLogForgingPatterns() {
        if (this.config == null) return null;

        Generator gen = get("log_forging");

        return gen == null ? null : gen.getLogForgingPatterns();
    }

    // ################## TestSuite ######################
    // type filter
    public Generator get(String type) {
        if (this.config.getGenerators() == null || CollectionUtils.isEmpty(this.config.getGenerators())) {
            return null;
        }

        return this.config.getGenerators().stream().filter(generator -> StringUtils.equals(generator.getType(), type)).findFirst().orElse(null);
//        return null;
    }

    public List<String> getAssertions(String type) {

        if (this.config == null) return DEFAULT_ASSERTIONS;

        String version = null;
        if (!CollectionUtils.isEmpty(this.config.getGenerators())) {
            for (Generator gen : this.config.getGenerators()) {
                if (StringUtils.equalsIgnoreCase(gen.getType(), type)) {
                    if (CollectionUtils.isEmpty(gen.getAssertions()) ||
                            gen.getAssertions().get(0) == null ||
                            gen.getAssertions().get(0).equalsIgnoreCase("null")) {
                        return DEFAULT_ASSERTIONS;
                    } else {
                        return gen.getAssertions();
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
        if (!CollectionUtils.isEmpty(this.config.getGenerators())) {
            for (Generator gen : this.config.getGenerators()) {
                if (StringUtils.equalsIgnoreCase(gen.getType(), type)) {
                    try {
                        severity = TestSuiteSeverity.valueOf(gen.getSeverity());
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
        if (!CollectionUtils.isEmpty(this.config.getGenerators())) {
            for (Generator gen : this.config.getGenerators()) {
                if (StringUtils.equalsIgnoreCase(gen.getType(), type)) {
                    return gen.getPostfix() != null ? gen.getPostfix() : gen.getType();
                }
            }
        }
        return type;
    }

    public boolean isInactive(String type) {

        Generator gen = get(type);
        if (gen != null) {
            //System.out.println(" ########## " + type + " " + ts.isInactive());
            return gen.isInactive();
        }
        //System.out.println(" ########## " + type + "  " + DEFAULT_INACTIVE);
        return DEFAULT_INACTIVE;
    }

    // ################## TestSuite end ######################

    // ###################     DDOS support   ###################
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

        Generator gen = get("DDOS");

        boolean isPresent = false;
        if (gen != null && !CollectionUtils.isEmpty(gen.getMatches())) {
            for (Match match : gen.getMatches()) {
                if (StringUtils.isEmpty(match.getResourceSamples())) {
                    continue;
                }
                for (String prop : match.getResourceSamples().split(",|\\n|\\s+")) {
                    //System.out.println (" Prop : " + prop + " Name: " + name + " Variables: " + match.getName());
                    if (prop.contains("=")) {
                        if (StringUtils.startsWithIgnoreCase(prop, name + "=")) {
                            return true;
                        }
                    } else {
                        if (StringUtils.equalsIgnoreCase(prop, name)) {
                            return true;
                        }
                    }

                }
            }
        }

        return isPresent || DEFAULT_DDOS_PARAM_NAMES.stream().filter(s -> StringUtils.equalsIgnoreCase(s, name)).findFirst().isPresent();

    }

    private static final String DEFAULT_DDOS_VALUE = "1001";

    public String getDDOSSupportedValue(String name) {
        if (StringUtils.isEmpty(name)) {
            return DEFAULT_DDOS_VALUE;
        }
        Generator gen = get("DDOS");

        if (gen == null || CollectionUtils.isEmpty(gen.getMatches())) {
            return DEFAULT_DDOS_VALUE;
        }

        for (Match match : gen.getMatches()) {
            if (StringUtils.isEmpty(match.getResourceSamples())) {
                continue;
            }
            for (String prop : match.getResourceSamples().split(",|\\n|\\t")) {
                if (prop.contains("=")) {
                    if (StringUtils.startsWithIgnoreCase(prop, name + "=")) {
                        return prop.split("=")[1];
                    }
                }
            }
        }

        return DEFAULT_DDOS_VALUE;
    }

    public String getDDOSMappedValue(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        Generator gen = get("DDOS");

        if (gen == null || CollectionUtils.isEmpty(gen.getMatches())) {
            return null;
        }

        for (Match match : gen.getMatches()) {
            if (StringUtils.isEmpty(match.getResourceSamples())) {
                continue;
            }
            for (String prop : match.getResourceSamples().split(",|\\n|\\t")) {
                if (prop.contains("?")) {
                    if (StringUtils.startsWithIgnoreCase(prop, path + "?")) {
                        return prop;
                    }
                }
            }
        }

        return null;
    }
    // ###################     DDOS support end   ###################

    // PropertyMapping

    public String getPropertyMapping(String node, String property) {

        if (this.config == null || this.config.getPropertyMapping() == null) {
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


    public ResourceSample getResourceSamples(String resource) {

        if (this.config == null || this.config.getResourceSamples() == null) {
            return null;
        }

        for (ResourceSample sample : config.getResourceSamples()) {
            if (StringUtils.equals(sample.getResource(), resource)) {
                return sample;
            }
        }

        return null;
    }

}
