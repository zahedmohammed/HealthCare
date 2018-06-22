package com.fxlabs.fxt.rest.run;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.Suite;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.run.RunService;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/job/{id}", method = RequestMethod.GET)
    public Response<List<Run>> findByJobId(@PathVariable("id") String id,
                                           @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return runService.findByJobId(id, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}/test-suite-responses", method = RequestMethod.GET)
    public Response<List<TestSuiteResponse>> findResponsesByRunId(@PathVariable("id") String id,
                                                                  @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                                  @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize
    ) {
        return runService.findByRunId(id, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    //testSuite
    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "{jobId}/testSuite/test-suite-responses/{name}", method = RequestMethod.GET)
    public Response<List<TestSuiteResponse>> findResponsesByTestSuite(@PathVariable("jobId") String jobId, @PathVariable("name") String testSuite,
                                                                  @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                                  @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize
    ) {
        return runService.findByTestSuite(jobId, testSuite, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}/test-suite-response/{name}", method = RequestMethod.GET)
    public Response<List<TestSuiteResponse>> findBySuiteId(@PathVariable("id") String id,
                                         @PathVariable("name") String name,
                                         @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                         @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_1k_PAGE_SIZE_VALUE, required = false) Integer pageSize
    ) {
        return runService.findByPk(id, name, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}/test-suite-summary", method = RequestMethod.GET)
    public Response<List<Suite>> findStatusByRunId(@PathVariable("id") String id,
                                                   @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                   @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_1k_PAGE_SIZE_VALUE, required = false) Integer pageSize
    ) {
        return runService.findSummaryByRunId(id, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, "failed")));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Run> findByRunId(@PathVariable("id") String id) {
        return runService.findById(id, SecurityUtil.getCurrentAuditor());
    }


    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/job/{id}", method = RequestMethod.POST)
    public Response<Run> run(@PathVariable("id") String id,
                             @RequestParam(value = "region", required = false) String region,
                             @RequestParam(value = "tags", required = false) String tags,
                             @RequestParam(value = "env", required = false) String env,
                             @RequestParam(value = "suites", required = false) String suites) {


        return runService.run(id, region, tags, env, suites, SecurityUtil.getCurrentAuditor());
    }


}
