package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectFile;
import com.fxlabs.fxt.dto.project.ProjectRequest;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.project.ProjectFileService;
import com.fxlabs.fxt.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(PROJECTS_BASE)
public class ProjectController {

    private ProjectFileService projectFileService;
    private ProjectService projectService;

    @Autowired
    public ProjectController(
            ProjectService projectService, ProjectFileService projectFileService) {
        this.projectService = projectService;
        this.projectFileService = projectFileService;
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Project> findById(@PathVariable("id") String id) {
        return projectService.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Project> update(@Valid @RequestBody Project dto) {
        return projectService.save(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Project> delete(@PathVariable("id") String id) {
        return projectService.delete(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Project>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return projectService.findProjects(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response<Project> add(@RequestBody ProjectRequest request) {
        return projectService.add(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}/git-account", method = RequestMethod.GET)
    public Response<ProjectRequest> findGitAccount(@PathVariable("id") String id) {
        return projectService.findGitByProjectId(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}/git-account", method = RequestMethod.PUT)
    public Response<ProjectRequest> saveGitAccount(@RequestBody ProjectRequest request) {
        return projectService.saveGitAccount(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public Response<Project> findByName(@PathVariable("name") String name) {
        return projectService.findByName(name, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}/project-checksums", method = RequestMethod.GET)
    public Response<List<ProjectFile>> findByProjectId(@PathVariable("id") String projectId, @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                       @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return projectFileService.findByProjectId(projectId, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }



}
