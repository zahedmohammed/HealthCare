package com.fxlabs.issues.services.project;

import com.fxlabs.issues.converters.project.ProjectConverter;
import com.fxlabs.issues.dao.entity.project.Issue;
import com.fxlabs.issues.dao.entity.users.Org;
import com.fxlabs.issues.dao.repository.jpa.IssueRepository;
import com.fxlabs.issues.dao.repository.jpa.OrgRepository;
import com.fxlabs.issues.dao.repository.jpa.ProjectRepository;
import com.fxlabs.issues.dao.repository.jpa.UsersRepository;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.NameDto;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.project.Project;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.fxlabs.issues.services.users.SystemSettingService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class ProjectServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.project.Project, Project, String> implements ProjectService {

    private ProjectRepository projectRepository;
    private ProjectConverter projectConverter;
    private UsersRepository usersRepository;
    private SystemSettingService systemSettingService;
    private OrgRepository orgRepository;
    private IssueRepository issueRepository;


    @Autowired
    public ProjectServiceImpl(ProjectRepository repository, ProjectConverter converter,
                              UsersRepository usersRepository, OrgRepository orgRepository,
                              SystemSettingService systemSettingService, IssueRepository issueRepository) {

        super(repository, converter);
        this.projectConverter = converter;
        this.projectRepository = repository;
        this.usersRepository = usersRepository;
        this.systemSettingService = systemSettingService;
        this.orgRepository = orgRepository;
        this.issueRepository = issueRepository;

    }


    @Override
    public Response<List<Project>> findProjects(String org, Pageable pageable) {
        Page<com.fxlabs.issues.dao.entity.project.Project> page = projectRepository.findByOrgIdAndInactive(org, false, pageable);
        return new Response<List<Project>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }


    @Override
    public Response<Project> save(Project dto, String user, String org) {


        if (dto == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for project"));
        }

        if (StringUtils.isEmpty(dto.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Name is empty"));
        }
        Org org_ = orgRepository.findById(org).get();
        NameDto o = new NameDto();
        o.setId(org_.getId());
        o.setName(org_.getName());
        dto.setOrg(o);
        Response<Project> projectResponse = super.save(dto, user);


        return projectResponse;
    }

    @Override
    public Response<Project> update(Project request, String user,  String org) {

        if (request == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for update Project "));
        }

        Optional<com.fxlabs.issues.dao.entity.project.Project> optionalProject = projectRepository.findByIdAndOrgId(request.getId(), org);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
        // check org
        if (!StringUtils.equals(request.getOrg().getId(), org)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid org"));
        }

        if (StringUtils.isEmpty(request.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project name"));
        }



        // check name is not duplicate
        Optional<com.fxlabs.issues.dao.entity.project.Project> projectOptional = this.projectRepository.findByNameIgnoreCaseAndIdNotLikeAndOrgIdAndInactive(request.getName(), request.getId(), request.getOrg().getId(), false);
        if (projectOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Project with name [%s] exists", request.getName())));
        }

        Project project = converter.convertToDto(optionalProject.get());

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return this.save(project, user);
    }




    @Override
    public Response<Project> delete(String id,  String user, String org) {
        // check user entitled to org
        Optional<com.fxlabs.issues.dao.entity.project.Project> optionalProject = projectRepository.findByIdAndOrgId(id, org);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }

        List<Issue> issueList = issueRepository.findByProjectId(id);

        for(Issue issue:issueList)
            issueRepository.delete(issue);

        return delete(id, user);
    }


    @Override
    public void isUserEntitled(String s, String user) {

    }
}
