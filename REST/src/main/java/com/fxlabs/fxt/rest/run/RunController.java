package com.fxlabs.fxt.rest.run;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.run.RunService;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(PROJECT_RUNS_BASE)
public class RunController {

    private RunService runService;
    private TestSuiteResponseService testSuiteResponseService;

    @Autowired
    public RunController(
            RunService service, TestSuiteResponseService testSuiteResponseService) {
        //super(service);
        this.runService = service;
        this.testSuiteResponseService = testSuiteResponseService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/job/{id}", method = RequestMethod.GET)
    public Response<List<Run>> findByJobId(@PathVariable("id") String id,
                                           @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return runService.findByJobId(id, SecurityUtil.getCurrentAuditor(), new PageRequest(0, 20));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}/test-suite-responses", method = RequestMethod.GET)
    public Response<List<TestSuiteResponse>> findResponsesByRunId(@PathVariable("id") String id,
                                                                  @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                                  @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize
    ) {
        return runService.findByRunId(id, SecurityUtil.getCurrentAuditor(), new PageRequest(page, pageSize, SORT_BY_CREATE_DT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Run> findByRunId(@PathVariable("id") String id) {
        return runService.findById(id, SecurityUtil.getCurrentAuditor());
    }


    @Secured(ROLE_USER)
    @RequestMapping(value = "/job/{id}", method = RequestMethod.POST)
    public Response<Run> run(@PathVariable("id") String id,
                             @RequestParam(value = "region", required = false) String region,
                             @RequestParam(value = "tags", required = false) String tags,
                             @RequestParam(value = "env", required = false) String env,
                             @RequestParam(value = "suites", required = false) String suites) {


        return runService.run(id, region, tags, env, suites, SecurityUtil.getCurrentAuditor());
    }


}
