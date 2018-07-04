package com.fxlabs.fxt.rest.dashboard;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.ProjectSaving;
import com.fxlabs.fxt.dto.users.Saving;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.clusters.ClusterService;
import com.fxlabs.fxt.services.it.IssueTrackerService;
import com.fxlabs.fxt.services.notify.NotificationService;
import com.fxlabs.fxt.services.project.EnvironmentService;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.project.ProjectService;
import com.fxlabs.fxt.services.project.TestSuiteService;
import com.fxlabs.fxt.services.run.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@RestController
@RequestMapping(DASHBOARD_BASE)
@Validated
public class DashboardController {

    private JobService jobService;
    private RunService runService;
    private ProjectService projectService;
    private EnvironmentService environmentService;
    private IssueTrackerService issueTrackerService;
    private ClusterService clusterService;
    private TestSuiteService testSuiteService;
    private NotificationService notificationAccountService;

    @Autowired
    public DashboardController(JobService jobService, RunService runService, ProjectService projectService, EnvironmentService environmentService,
                               IssueTrackerService skillSubscriptionService, ClusterService clusterService, TestSuiteService testSuiteService,
                               NotificationService notificationAccountService) {
        this.jobService = jobService;
        this.runService = runService;
        this.projectService = projectService;
        this.environmentService = environmentService;
        this.issueTrackerService = skillSubscriptionService;
        this.clusterService = clusterService;
        this.testSuiteService = testSuiteService;
        this.notificationAccountService = notificationAccountService;
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-projects", method = RequestMethod.GET)
    public Response<Long> countProjects() {
        return projectService.countProjects(SecurityUtil.getOrgId());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-envs", method = RequestMethod.GET)
    public Response<Long> countEnvs(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                    @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) @Max(100) Integer pageSize) {
        return environmentService.count(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-jobs", method = RequestMethod.GET)
    public Response<Long> countJobs(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                    @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) @Max(100) Integer pageSize) {
        return jobService.count(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-suites", method = RequestMethod.GET)
    public Response<Long> countSuites(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                      @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) @Max(100) Integer pageSize) {
        return testSuiteService.count(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-runs", method = RequestMethod.GET)
    public Response<Long> countRuns(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                    @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) @Max(100) Integer pageSize) {
        return runService.count(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-tests", method = RequestMethod.GET)
    public Response<Long> countTests(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) @Max(100) Integer pageSize) {
        return runService.countTests(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-time", method = RequestMethod.GET)
    public Response<Long> countTime(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                    @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) @Max(100) Integer pageSize) {
        return runService.countTime(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-bytes", method = RequestMethod.GET)
    public Response<Long> countBytes(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) @Max(100) Integer pageSize) {
        return runService.countBytes(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-ibots", method = RequestMethod.GET)
    public Response<Long> countiBots() {
        return issueTrackerService.count(SecurityUtil.getOrgId());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-channels", method = RequestMethod.GET)
    public Response<Long> countChannels() {
        return notificationAccountService.count(SecurityUtil.getOrgId());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/count-ebots", method = RequestMethod.GET)
    public Response<Long> countBots() {
        return clusterService.countBotRegions(SecurityUtil.getOrgId());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/bots/{id}/savings", method = RequestMethod.GET)
    public Response<Saving> getSavingsById(@PathVariable("id") String id) {
        return clusterService.savings(id, SecurityUtil.getOrgId());
    }


    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/projects/{id}/savings", method = RequestMethod.GET)
    public Response<ProjectSaving> getProjectSavings(@PathVariable("id") String id) {
        return projectService.getProjectSavings(id, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }


}
