package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectFile;
import com.fxlabs.fxt.dto.project.ProjectImports;
import com.fxlabs.fxt.dto.project.ProjectSaving;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.project.ProjectFileService;
import com.fxlabs.fxt.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(PROJECTS_BASE)
@Validated
public class ProjectController {

    private ProjectFileService projectFileService;
    private ProjectService projectService;
    private static final String SAVINGS = "/savings";

    @Autowired
    public ProjectController(
            ProjectService projectService, ProjectFileService projectFileService) {
        this.projectService = projectService;
        this.projectFileService = projectFileService;
    }

    @Secured({ROLE_USER})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Project>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Max(20) Integer pageSize) {
        return projectService.findProjects(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Project> findById(@PathVariable("id") String id) {
        return projectService.findById(id, SecurityUtil.getOrgId());
    }

//    @Secured(ROLE_USER)
//    @RequestMapping(value = "/{id}"+SAVINGS, method = RequestMethod.GET)
//    public Response<ProjectSaving> getProjectSavings(@PathVariable("id") String id) {
//        return projectService.getProjectSavings(id, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
//    }

    @Secured(ROLE_PROJECT_MANAGER)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Project> update(@Valid @RequestBody Project dto) {
        return projectService.save(dto, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_PROJECT_MANAGER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Project> delete(@PathVariable("id") String id) {
        return projectService.delete(id, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_PROJECT_MANAGER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Project> add(@RequestBody Project request) {
        return projectService.add(request, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_PROJECT_MANAGER)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Project> save(@RequestBody Project request) {
        return projectService.saveProject(request, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_PROJECT_MANAGER)
    @RequestMapping(value = "/find-by-name/{org}/{name}", method = RequestMethod.GET)
    public Response<Project> findByProjectName(@PathVariable("org") String org, @PathVariable("name") String name) {
        return projectService.findByOrgAndName(org + "/" + name, SecurityUtil.getOrgId());
    }

    @Secured(ROLE_PROJECT_MANAGER)
    @RequestMapping(value = "/{id}/project-imports", method = RequestMethod.POST)
    public Response<Boolean> saveImports(@RequestBody ProjectImports request) {
        return projectService.saveProjectImports(request, SecurityUtil.getOrgId());
    }


    @Secured(ROLE_PROJECT_MANAGER)
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public Response<Project> findByName(@PathVariable("name") String name) {
        return projectService.findByName(name, SecurityUtil.getOrgId());
    }

    @Secured(ROLE_PROJECT_MANAGER)
    @RequestMapping(value = "/{id}/project-checksums", method = RequestMethod.GET)
    public Response<List<ProjectFile>> findByProjectId(@PathVariable("id") String projectId,
                                                       @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                       @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return projectFileService.findByProjectId(projectId, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }


}
