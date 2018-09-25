package com.fxlabs.fxt.rest.run;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.TestSuite;
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

import javax.validation.Valid;
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
    public static final Sort RUN_LIST_SORT = new Sort(Sort.Direction.DESC, "runId", "modifiedDate");

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
        return runService.findByJobId(id, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, RUN_LIST_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}/test-suite-responses", method = RequestMethod.GET)
    public Response<List<TestSuiteResponse>> findResponsesByRunId(@PathVariable("id") String id,
                                                                  @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                                  @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize
    ) {
        return runService.findByRunId(id, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, RUN_LIST_SORT));
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
        return runService.findSummaryByRunId(id, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, "failed")));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}/test-suite-summary/search", method = RequestMethod.GET)
    public Response<List<Suite>> search(@PathVariable("id") String id,
                                        @RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "keyword", required = false) String keyword,
                                        @RequestParam(value = "status", required = false) String status,
                                        @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                        @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_1k_PAGE_SIZE_VALUE, required = false) Integer pageSize
    ) {
        return runService.search(id, category, keyword, status, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, "failed")));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/stoprun/{id}", method = RequestMethod.GET)
    public Response<Run> stopRun(@PathVariable("id") String id) {
        return runService.stopRun(id, SecurityUtil.getCurrentAuditor());
    }


    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Run> findByRunId(@PathVariable("id") String id) {
        return runService.findRunById(id, SecurityUtil.getCurrentAuditor());
    }
  @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/job/{id}/runNo/{runNo}", method = RequestMethod.GET)
    public Response<Run> findByRunNumber(@PathVariable("id") String id,@PathVariable("runNo") Long runNo) {
        return runService.findRunByJobIdAndRunNo(id,runNo, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/job/{id}", method = RequestMethod.POST)
    public Response<Run> run(@PathVariable("id") String id,
                             @RequestParam(value = "region", required = false) String region,
                             @RequestParam(value = "tags", required = false) String tags,
                             @RequestParam(value = "env", required = false) String env,
                             @RequestParam(value = "suites", required = false) String suites,
                             @RequestParam(value = "categories", required = false) String categories) {


        return runService.run(id, region, tags, env, suites, categories, SecurityUtil.getCurrentAuditor());
    }



    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/testsuite", method = RequestMethod.POST)
    public Response<List<TestSuiteResponse>> runTesSuite(@RequestParam(value = "region", required = true) String region,
                                                         @RequestParam(value = "env", required = true) String env,
                                                         @Valid @RequestBody TestSuite dto) {


        return runService.runTestSuite(region, env, SecurityUtil.getCurrentAuditor(), dto);
    }


    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/job/{jobId}/run/{runId}", method = RequestMethod.DELETE)
    public Response<Run> deleteRun(@PathVariable("jobId") String jobId,
                                   @PathVariable("runId") Long runId) {
        return runService.deleteRun(jobId, runId, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/job/{jobId}/rerun/{runId}", method = RequestMethod.POST)
    public Response<Run> reRun(@PathVariable("jobId") String jobId,
                                   @PathVariable("runId") Long runId) {
        return runService.reRun(jobId, runId, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Run> delete(@PathVariable("id") String id) {
        return runService.delete(id, SecurityUtil.getCurrentAuditor());
    }
}
