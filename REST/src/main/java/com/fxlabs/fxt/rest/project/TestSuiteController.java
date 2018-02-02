package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.TestSuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fxlabs.fxt.rest.base.BaseController.TEST_SUITES_BASE;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(TEST_SUITES_BASE)
public class TestSuiteController extends BaseController<TestSuite, String> {

    @Autowired
    public TestSuiteController(
            TestSuiteService service) {
        super(service);
    }


}
