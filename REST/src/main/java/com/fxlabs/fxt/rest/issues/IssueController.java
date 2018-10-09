package com.fxlabs.fxt.rest.issues;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.issue.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

@RestController
@RequestMapping(ISSUE_BASE)
public class IssueController {

    private IssueService issueService;

    @Autowired
    public IssueController(IssueService service) {
        this.issueService = service;
    }


    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/job-id/{id}", method = RequestMethod.GET)
    public Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>> findByJobId(@PathVariable("id") String id, @RequestParam("status") String status, @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                                                          @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return issueService.findIssuesByJobId(id, status, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/env-id/{id}", method = RequestMethod.GET)
    public Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>> findByEnvId(@PathVariable("id") String id, @RequestParam("status") String status, @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                                                          @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return issueService.findIssuesByEnvId(id, status, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/project-id/{id}", method = RequestMethod.GET)
    public Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>> findByProjectId(@PathVariable("id") String id, @RequestParam("status") String status, @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                                                              @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return issueService.findIssuesByProjectId(id, status, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }
}
