package com.fxlabs.fxt.rest.dashboard;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.skills.SkillType;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.clusters.ClusterService;
import com.fxlabs.fxt.services.notify.NotificationAccountService;
import com.fxlabs.fxt.services.project.EnvironmentService;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.project.ProjectService;
import com.fxlabs.fxt.services.project.TestSuiteService;
import com.fxlabs.fxt.services.run.RunService;
import com.fxlabs.fxt.services.skills.SkillSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 *
 */
@RestController
@RequestMapping(DASHBOARD_BASE)
public class DashboardController {

    private JobService jobService;
    private RunService runService;
    private ProjectService projectService;
    private EnvironmentService environmentService;
    private SkillSubscriptionService skillSubscriptionService;
    private ClusterService clusterService;
    private TestSuiteService testSuiteService;
    private NotificationAccountService notificationAccountService;

    @Autowired
    public DashboardController(JobService jobService, RunService runService, ProjectService projectService, EnvironmentService environmentService,
                               SkillSubscriptionService skillSubscriptionService, ClusterService clusterService, TestSuiteService testSuiteService,
                               NotificationAccountService notificationAccountService) {
        this.jobService = jobService;
        this.runService = runService;
        this.projectService = projectService;
        this.environmentService = environmentService;
        this.skillSubscriptionService = skillSubscriptionService;
        this.clusterService = clusterService;
        this.testSuiteService = testSuiteService;
        this.notificationAccountService = notificationAccountService;
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-projects", method = RequestMethod.GET)
    public Response<Long> countProjects() {
        return projectService.countProjects(SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-envs", method = RequestMethod.GET)
    public Response<Long> countEnvs(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return environmentService.count(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-jobs", method = RequestMethod.GET)
    public Response<Long> countJobs(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return jobService.count(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-suites", method = RequestMethod.GET)
    public Response<Long> countSuites(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                    @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return testSuiteService.count(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-runs", method = RequestMethod.GET)
    public Response<Long> countRuns(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return runService.count(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-tests", method = RequestMethod.GET)
    public Response<Long> countTests(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return runService.countTests(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-time", method = RequestMethod.GET)
    public Response<Long> countTime(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return runService.countTime(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-bytes", method = RequestMethod.GET)
    public Response<Long> countBytes(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return runService.countBytes(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-ibots", method = RequestMethod.GET)
    public Response<Long> countiBots() {
        return skillSubscriptionService.count(SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-channels", method = RequestMethod.GET)
    public Response<Long> countChannels() {
        return notificationAccountService.count(SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/count-ebots", method = RequestMethod.GET)
    public Response<Long> counteBots() {
        return clusterService.countBotRegions(SecurityUtil.getCurrentAuditor());
    }



}
