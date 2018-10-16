package com.fxlabs.fxt.codegen.generators.rbac;

import com.fxlabs.fxt.codegen.code.Generator;
import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.HttpMethod;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component(value = "rBACGenerator")
public class RBACGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "rbac";
    //protected static final String AUTH = "";
    //protected static final String OPERAND = "403";
    protected static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {


        Generator generator = configUtil.get(SCENARIO);
        if (generator == null || generator.isInactive()) {
            return null;
        }


        final String path_ = path;

        for (Parameter param : op.getParameters()) {
            if (param instanceof PathParameter) {
                PathParameter pathParam = (PathParameter) param;
                String name = pathParam.getName();
                String defaultVal = pathParam.getDefault() != null ? pathParam.getDefault().toString() : "{{@Random}}";
                path = path.replaceAll("\\{" + name + "\\}", defaultVal);
            }
        }

        // Need to build body
        // only assertions and auth are required post path pattern matches


        final String endPoint = path;


        String postFix = configUtil.getTestSuitePostfix(SCENARIO);

        Set<String> allowedAssertions = configUtil.getAllowedAssertions();
        Set<String> disallowedAssertions = configUtil.getDisallowedAssertions();

        Set<String> allRoles = configUtil.getAllRoles();
        Set<String> allowedRoles = configUtil.getAllowedRoles(method.name(), path);

        String testcase = null;
        // Only POST & PUT
        if (method == HttpMethod.POST || method == HttpMethod.PUT) {

            // Only body Parameter
            Model model = null;
            for (Parameter p : op.getParameters()) {
                if (!(p instanceof BodyParameter)) {
                    continue;
                }
                model = ((BodyParameter) p).getSchema();
            }

            if (model != null || !StringUtils.isBlank(model.getReference())) {
                testcase = factory.getValid(model.getReference());
            }

        }


        List<TestSuiteMin> list = new ArrayList<>();

        allRoles.stream().forEach(role -> {
            if (CollectionUtils.containsAny(allRoles, allowedRoles)) {
                list.addAll(build(op, path_, endPoint, role + "_Allowed_" + postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, role, null, allowedAssertions));
            } else {
                list.addAll(build(op, path_, endPoint, role + "_Disallowed_" + postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, role, null, disallowedAssertions));
            }

        });

        if (testcase != null) {
            for (TestSuiteMin ts : list) {
                buildTestCase(ts, 1, testcase);
            }
        }

        return list;
    }
}
