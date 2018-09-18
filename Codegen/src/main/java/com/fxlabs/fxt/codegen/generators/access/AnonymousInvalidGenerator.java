package com.fxlabs.fxt.codegen.generators.access;

import com.fxlabs.fxt.codegen.code.Generator;
import com.fxlabs.fxt.codegen.code.Match;
import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.Policies;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component(value = "anonymousInvalidGenerator")
public class AnonymousInvalidGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "anonymous_invalid";
    protected static final String AUTH = "Anonymous";
    protected static final String OPERAND = "401";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        // TODO buildTestCase(testSuite)
        if (method == io.swagger.models.HttpMethod.POST || method == io.swagger.models.HttpMethod.PUT) {
            return null;
        }

        Generator generator = configUtil.get(SCENARIO);
        if (generator == null || generator.isInactive()) {
            return null;
        }

        String endPoint = path;

        for (Parameter param : op.getParameters()) {
            if (param instanceof QueryParameter) {
                QueryParameter queryParam = (QueryParameter) param;
                String name = queryParam.getName();
                String defaultVal = queryParam.getDefault() != null ? queryParam.getDefault().toString() : "{{@RandomInteger}}";
                endPoint = endPoint.replaceAll("\\{" + name + "\\}", defaultVal);
            }
            if (param instanceof PathParameter) {
                PathParameter queryParam = (PathParameter) param;
                String name = queryParam.getName();
                String defaultVal = queryParam.getDefault() != null ? queryParam.getDefault().toString() : "{{@Random}}";
                endPoint = endPoint.replaceAll("\\{"+name+"\\}" , defaultVal);
            }
        }

        Policies policies = null;

        String postFix = configUtil.getTestSuitePostfix(SCENARIO);
        List<TestSuiteMin> list = build(op, path, endPoint, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies);


        //  Non-Secured Paths

        if (CollectionUtils.isEmpty(generator.getMatches())) {
            return list;
        }

        final String path_ = path;
        Match match = null;

        for (Match m : generator.getMatches()) {
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
            System.out.println(String.format("No Non-Secured matches found for path [%s]...", path_));
        }else {

            List<String> assertions = generator.getAssertions();
            if (CollectionUtils.isEmpty(assertions)) {
                assertions = Arrays.asList("@StatusCode == 200");
            }
            list.addAll(build(op, path, endPoint, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies));
        }
        return list;
    }
}
