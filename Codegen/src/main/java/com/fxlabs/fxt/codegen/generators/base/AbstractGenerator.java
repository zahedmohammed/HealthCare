package com.fxlabs.fxt.codegen.generators.base;

import com.fxlabs.fxt.codegen.code.StubHandler;
import com.fxlabs.fxt.codegen.generators.json.JSONFactory;
import com.fxlabs.fxt.codegen.generators.utils.AutoCodeConfigUtil;
import com.fxlabs.fxt.codegen.generators.utils.NameUtil;
import com.fxlabs.fxt.dto.project.*;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public abstract class AbstractGenerator implements Generator {

    final protected String APPLICATION_JSON = "application/json";
    final protected String JSON_CONTENT_TYPE = "Content-Type: application/json";
    final protected String JSON_ACCEPT = "Accept: application/json";
    final protected String STATUS_CODE_ASSERTION = "@StatusCode";
    final protected String EQUALS = "==";
    final protected String NOT_EQUALS = "!=";
    final protected String TAG = "V1";
    final protected String AUTHOR = "FX Bot";

    @Autowired
    protected StubHandler stubHandler;

    @Autowired
    protected JSONFactory factory;

    @Autowired
    protected AutoCodeConfigUtil configUtil;

    @PostConstruct
    public void register() {
        this.stubHandler.register(this);
    }

    public List<TestSuiteMin> build(Operation op, String path, String postfix, String scenario, String description, TestSuiteType testSuiteType,
                                    io.swagger.models.HttpMethod method, String tag, String auth) {
        return build(op, path, postfix, scenario, description, testSuiteType, method, tag, auth, null, false);
    }

    public List<TestSuiteMin> build(Operation op, String path, String postfix, String type, String description, TestSuiteType testSuiteType,
                                    io.swagger.models.HttpMethod method, String tag, String auth, Policies policies, boolean inactive) {

        TestSuiteMin testSuite = new TestSuiteMin();
        testSuite.setSeverity(configUtil.getTestSuiteSeverity(type));
        testSuite.setCategory(configUtil.getTestSuiteCategory(type));
        addAssertions(testSuite, configUtil.getAssertions(type));
        inactive = configUtil.isInactive(type);

        List<TestSuiteMin> list = new ArrayList<>();

        // Don't create file if inactive is true.
        if (inactive) {
            return list;
        }

        list.add(testSuite);

        // TODO - replace path-params and query-params
        buildName(testSuite, path, method, postfix)
                .buildInactive(testSuite, inactive)
                //buildFileName(testSuite, name)
                .buildDescription(testSuite, description)
                .buildType(testSuite, testSuiteType)
                .buildMethod(testSuite, method)
                .buildEndpoint(testSuite, path, op)
                .buildHeader(testSuite, op)
                // TODO buildTestCase(testSuite)
                // TODO buildAssertion(testSuite, )
                // TODO buildInit(testSuite)
                // TODO buildCleanup(testSuite)
                .buildTag(testSuite, tag)
                .buildAuth(testSuite, auth)
                .buildAuthor(testSuite, AUTHOR)
                .buildPolicies(testSuite, policies);

        return list;

    }

    protected AbstractGenerator buildInactive(TestSuiteMin testSuite, boolean inactive) {

        testSuite.setInactive(inactive);

        return this;

    }

    protected AbstractGenerator buildName(TestSuiteMin testSuite, String path, io.swagger.models.HttpMethod method, String postfix) {

        //System.out.println("path : " + path);

        String base = NameUtil.extract(path);
        StringJoiner joiner = new StringJoiner("_");
        joiner.add(base);

        testSuite.setParent(joiner.toString());

        String name = joiner.add(method.name().toLowerCase())
                .add(postfix).toString();

        testSuite.setName(name);

        return this;

    }

    protected AbstractGenerator buildFileName(TestSuiteMin testSuite, String fileName) {
        testSuite.setName(fileName);
        return this;
    }

    protected AbstractGenerator buildDescription(TestSuiteMin testSuite, String description) {
        testSuite.setDescription(description);
        return this;
    }

    protected AbstractGenerator buildType(TestSuiteMin testSuite, TestSuiteType testSuiteType) {
        if (testSuiteType == null) {
            testSuiteType = TestSuiteType.SUITE;
        }
        testSuite.setType(testSuiteType);

        return this;
    }

    protected AbstractGenerator buildEndpoint(TestSuiteMin testSuite, String path, Operation op) {

        String endpoint = path;
        boolean first = true;
        for (Parameter p : op.getParameters()) {
            if (p instanceof QueryParameter) {
                QueryParameter qp = (QueryParameter) p;
                if (first) {
                    endpoint += "?";
                    endpoint += qp.getName() + "=";
                    first = false;
                } else {
                    endpoint += "&" + qp.getName() + "=";
                }

            } else if (p instanceof PathParameter) {
                PathParameter bp = (PathParameter) p;
            }

            /*else if (p instanceof BodyParameter) {
                BodyParameter bp = (BodyParameter) p;
            }*/
        }

        testSuite.setEndpoint(path);
        return this;
    }

    protected AbstractGenerator buildMethod(TestSuiteMin testSuite, io.swagger.models.HttpMethod method) {

        // Handle HttpMethod
        testSuite.setMethod(HttpMethod.valueOf(method.name()));
        return this;
    }

    protected AbstractGenerator buildHeader(TestSuiteMin testSuite, Operation op) {

        // Headers
        if (testSuite.getHeaders() == null) {
            testSuite.setHeaders(new ArrayList<>());
        }

        //if (CollectionUtils.containsInstance(op.getConsumes(), APPLICATION_JSON)) {
        testSuite.getHeaders().add(JSON_ACCEPT);
        //}
        //if (CollectionUtils.containsInstance(op.getProduces(), APPLICATION_JSON)) {
        testSuite.getHeaders().add(JSON_CONTENT_TYPE);
        //}

        return this;
    }

    protected AbstractGenerator buildTestCase(TestSuiteMin testSuite, int no, String testCase) {

        // Assertions
        if (testSuite.getTestCases() == null) {
            testSuite.setTestCases(new ArrayList<>());
        }

        TestCase tc = new TestCase();
        tc.setId(no);
        tc.setBody(testCase);
        testSuite.getTestCases().add(tc);

        return this;
    }

    protected AbstractGenerator buildAssertion(TestSuiteMin testSuite, String operand1, String operation, String operand2) {

        // Assertions
        if (testSuite.getAssertions() == null) {
            testSuite.setAssertions(new ArrayList<>());
        }

        testSuite.getAssertions().add(String.format("%s %s %s", operand1, operation, operand2));

        return this;
    }

    protected AbstractGenerator addAssertions(TestSuiteMin testSuite, List<String> assertions) {

        // Assertions
        if (testSuite.getAssertions() == null) {
            testSuite.setAssertions(new ArrayList<>());
        }

        if (assertions != null)
            testSuite.getAssertions().addAll(assertions);

        return this;
    }

    protected AbstractGenerator buildTag(TestSuiteMin testSuite) {
        return buildTag(testSuite, "V1");
    }

    protected AbstractGenerator buildTag(TestSuiteMin testSuite, String tag) {

        // Tags
        if (testSuite.getTags() == null) {
            testSuite.setTags(new ArrayList<>());
        }
        testSuite.getTags().add(tag);

        return this;
    }

    protected AbstractGenerator buildAuthor(TestSuiteMin testSuite, String author) {

        // Tags
        if (testSuite.getAuthors() == null) {
            testSuite.setTags(new ArrayList<>());
        }
        testSuite.getAuthors().add(author);

        return this;
    }

    protected AbstractGenerator buildAuth(TestSuiteMin testSuite, String auth) {

        testSuite.setAuth(auth);

        return this;
    }

    protected AbstractGenerator buildInit(TestSuiteMin testSuite, String init) {

        // Tags
        /*if (testSuite.getInit() == null) {
            testSuite.setInit(new ArrayList<>());
        }
        testSuite.getInit().add(init);*/

        return this;
    }

    protected AbstractGenerator buildCleanup(TestSuiteMin testSuite, String cleanup) {

        // Tags
        /*if (testSuite.getCleanup() == null) {
            testSuite.setCleanup(new ArrayList<>());
        }
        testSuite.getInit().add(cleanup);*/

        return this;
    }

    protected AbstractGenerator buildPolicies(TestSuiteMin testSuite, Policies policies) {

        // Tags
        /*if (testSuite.getCleanup() == null) {
            testSuite.setCleanup(new ArrayList<>());
        }
        testSuite.getInit().add(cleanup);*/

        if (policies != null) {
            testSuite.setPolicies(policies);
        }
        return this;
    }

}
