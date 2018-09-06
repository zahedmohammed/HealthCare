package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.code.Generator;
import com.fxlabs.fxt.codegen.code.Match;
import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
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

/**
 * @author Mohammed Luqman Shareef
 * @since 8/9/2018
 */
@Component(value = "slaGetGenerator")
public class SLAGetGenerator extends AbstractGenerator {

    protected static final String TYPE = "sla";
//    protected static final String PARAM_TYPE = "sla";
    protected static final String AUTH = "Default";
    protected static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        if (method != io.swagger.models.HttpMethod.GET) {
            return null;
        }

        for (Parameter param : op.getParameters()) {
            if (param instanceof PathParameter) {
                PathParameter pathParam = (PathParameter) param;
                String name = pathParam.getName();

                String defaultVal = "";
                if (pathParam.getDefault() != null){
                    defaultVal = pathParam.getDefault().toString();
                }else{

                    defaultVal = "{{@Random}}";
                }
                path = path.replaceAll("\\{" + name + "\\}", defaultVal);
            }
            if (param instanceof QueryParameter) {
                QueryParameter queryParam = (QueryParameter) param;
            }

        }

        Generator generator = configUtil.get(TYPE);
        if (generator == null || generator.isInactive()) {
            return null;
        }

        if (CollectionUtils.isEmpty(generator.getMatches())) {
            return null;
        }

        final String path_ = path;
        Match match = null;

        for (Match m : generator.getMatches()) {
            if (StringUtils.isEmpty(m.getPathPatterns())) {
                m.setPathPatterns("/**");
//                continue;
            }
            if (StringUtils.isEmpty(m.getMethods())) {
                m.setMethods("Get");
//                continue;
            }
            for (String pattern : m.getPathPatterns().split(", ")) {
                if (ANT_PATH_MATCHER.match(pattern, path_) &&
                        (StringUtils.isEmpty(m.getMethods()) || org.apache.commons.lang3.StringUtils.containsIgnoreCase(m.getMethods(), method.name()))) {
                    System.out.print(String.format("Match found for pattern [%s] and path [%s]", pattern, path_));
                    match = m;
                    System.out.println("Match : " + m.getPathPatterns());
                    break;
                }
            }
            if (match != null) break;
        }

        if (match == null) {
            System.err.println(String.format("No SLA matches found for path [%s]...", path_));
            return null;
        }

        String value = match.getValue();
        if (StringUtils.isEmpty(value)){
            value = "3000";
        }

        String rtAssertion = "@ResponseTime <= " + value;

        List<String> assertions = generator.getAssertions();
        if (assertions == null ){
            assertions = new ArrayList<String>();
        }

        boolean rtAssertionUpdated = false;
        for (int i=0; i<assertions.size(); i++){

            if (assertions.get(i).trim().startsWith("@ResponseTime")){
                assertions.set(i,rtAssertion);
                rtAssertionUpdated = true;
                break;
            }
        }

        if (! rtAssertionUpdated  ){
            assertions.add(rtAssertion);
        }

        String postFix = configUtil.getTestSuitePostfix(TYPE);

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        List<TestSuiteMin> testSuites = build(op, path, postFix, TYPE, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
        for (TestSuiteMin testSuite : testSuites) {
            testSuite.setEndpoint(path );
            System.out.println(testSuite.getName());
        }
        allTestSuites.addAll(testSuites);
        return allTestSuites;

    }
}
