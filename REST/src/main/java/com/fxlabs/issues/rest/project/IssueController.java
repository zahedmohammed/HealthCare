package com.fxlabs.issues.rest.project;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.project.Issue;
import com.fxlabs.issues.dto.project.Project;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.project.IssueService;
import com.fxlabs.issues.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.DEFAULT_SORT;
import static com.fxlabs.issues.rest.base.BaseController.ROLE_PROJECT_MANAGER;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@RestController
@RequestMapping(BaseController.ISSUE_BASE)
@Validated
public class IssueController {

    private IssueService issueService;


    @Autowired
    public IssueController(
            IssueService issueService) {
        this.issueService = issueService;
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
    public Response<List<Issue>> findByProjectId(@PathVariable("projectId") String projectId, @RequestParam(value = BaseController.PAGE_PARAM, defaultValue = BaseController.DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                         @RequestParam(value = BaseController.PAGE_SIZE_PARAM, defaultValue = BaseController.DEFAULT_PAGE_SIZE_VALUE, required = false) @Max(20) Integer pageSize) {

        return issueService.findIssues(projectId, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));

    }

    @Secured(BaseController.ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Issue> findById(@PathVariable("id") String id) {
        return issueService.findById(id, SecurityUtil.getOrgId());
    }


    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Issue> update(@Valid @RequestBody Issue dto) {
        return issueService.update(dto, SecurityUtil.getCurrentAuditor(), SecurityUtil.getOrgId());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/project/{projectId}/issue/{id}", method = RequestMethod.DELETE)
    public Response<Issue> delete(@PathVariable("id") String id, @PathVariable("projectId") String projectId) {
        return issueService.delete(id, projectId, SecurityUtil.getOrgId());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Issue> add(@RequestBody Issue request) {
        return issueService.create(request,  SecurityUtil.getCurrentAuditor(), SecurityUtil.getOrgId());
    }

}
