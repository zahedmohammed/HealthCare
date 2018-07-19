package com.fxlabs.fxt.codegen.generators.rbac;

import com.fxlabs.fxt.codegen.code.Match;
import com.fxlabs.fxt.codegen.code.TestSuite;
import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component(value = "rBACGenerator")
public class RBACGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "rbac";
    //protected static final String AUTH = "";
    //protected static final String OPERAND = "403";
    protected static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        for (Parameter param : op.getParameters()) {
            if (param instanceof PathParameter) {
                PathParameter pathParam = (PathParameter) param;
                String name = pathParam.getName();
                String defaultVal = pathParam.getDefault() != null ? pathParam.getDefault().toString() : "{{@Random}}";
                path = path.replaceAll("\\{" + name + "\\}", defaultVal);
            }
        }

        // No need to build body
        // only assertions and auth are required post path pattern matches

        TestSuite testSuite = configUtil.get(SCENARIO);
        if (testSuite == null || testSuite.isInactive()) {
            System.err.println("No RBAC configuration found or RBAC is inactive...");
            return null;
        }

        if (CollectionUtils.isEmpty(testSuite.getMatches())) {
            System.err.println("No RBAC matches configuration found...");
            return null;
        }

        final String path_ = path;
        Match match = null;
        for (Match m : testSuite.getMatches()) {
            if (StringUtils.isEmpty(m.getPathPatterns())) {
                continue;
            }
            for (String pattern : m.getPathPatterns().split(", ")) {
                if (ANT_PATH_MATCHER.match(pattern, path_) &&
                        (StringUtils.isEmpty(m.getMethods()) || org.apache.commons.lang3.StringUtils.containsIgnoreCase(m.getMethods(), method.name()))) {
                    System.out.print(String.format("Match found for pattern [%s] and path [%s]", pattern, path_));
                    match = m;
                    break;
                }
            }
            if (match != null) break;
        }

        if (match == null) {
            System.err.println(String.format("No RBAC matches found for path [%s]...", path_));
            return null;
        }


        String postFix = configUtil.getTestSuitePostfix(SCENARIO);

        List<String> assertions = testSuite.getAssertions();
        if (CollectionUtils.isEmpty(assertions)) {
            assertions = Arrays.asList("@StatusCode == 403");
        }

        // TODO get role not in
        String roles = "Anonymous";
        if (!StringUtils.isEmpty(match.getDenyRoles())) {
            roles = match.getDenyRoles();
        }

        List<TestSuiteMin> list = new ArrayList<>();

        Arrays.stream(roles.split(", ")).forEach(role -> {
            list.addAll(build(op, path_, role + "_" + postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, role));
        });

        return list;
    }
}
