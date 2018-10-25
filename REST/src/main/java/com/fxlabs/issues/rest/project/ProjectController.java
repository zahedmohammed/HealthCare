package com.fxlabs.issues.rest.project;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.project.ProjectService;
import com.fxlabs.issues.dto.project.Project;
import com.fxlabs.issues.rest.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping(BaseController.PROJECTS_BASE)
@Validated
public class ProjectController {

    private ProjectService projectService;


    @Autowired
    public ProjectController(
            ProjectService projectService) {
        this.projectService = projectService;
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Project>> findAll(@RequestParam(value = BaseController.PAGE_PARAM, defaultValue = BaseController.DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                           @RequestParam(value = BaseController.PAGE_SIZE_PARAM, defaultValue = BaseController.DEFAULT_PAGE_SIZE_VALUE, required = false) @Max(20) Integer pageSize) {

        return projectService.findProjects(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));

    }

    @Secured(BaseController.ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Project> findById(@PathVariable("id") String id) {
        return projectService.findById(id, SecurityUtil.getOrgId());
    }


    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Project> update(@Valid @RequestBody Project dto) {
        return projectService.save(dto, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Project> delete(@PathVariable("id") String id) {
        //return projectService.delete(id, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
        return null;
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Project> add(@RequestBody Project request) {
        return projectService.save(request,  SecurityUtil.getCurrentAuditor(), SecurityUtil.getOrgId());
    }

}
