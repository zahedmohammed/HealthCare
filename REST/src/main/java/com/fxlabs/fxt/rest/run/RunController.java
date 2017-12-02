package com.fxlabs.fxt.rest.run;

import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.run.RunService;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_RUNS_BASE;

@RestController
@RequestMapping(PROJECT_RUNS_BASE)
public class RunController extends BaseController<Run, String> {

    private RunService runService;
    private TestSuiteResponseService testSuiteResponseService;

    @Autowired
    public RunController(
            RunService service, TestSuiteResponseService testSuiteResponseService) {
        super(service);
        this.runService = service;
        this.testSuiteResponseService = testSuiteResponseService;
    }

    @RequestMapping(value = "/{id}/test-suites", method = RequestMethod.GET)
    public Response<List<TestSuiteResponse>> run(@PathVariable("id") String id,
                                                 @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                 @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize
    ) {
        return runService.findByRunId(id, new PageRequest(page, pageSize, SORT_BY_CREATE_DT));
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Response<Run> run(@PathVariable("id") String id) {
        return runService.run(id);
    }


}
