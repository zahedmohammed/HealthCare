package com.fxlabs.issues.services.project;

import com.fxlabs.issues.converters.project.IssueConverter;
import com.fxlabs.issues.converters.project.ProjectConverter;
import com.fxlabs.issues.converters.users.OrgConverter;
import com.fxlabs.issues.dao.entity.project.Project;
import com.fxlabs.issues.dao.repository.es.IssueESRepository;
import com.fxlabs.issues.dao.repository.jpa.IssueRepository;
import com.fxlabs.issues.dao.repository.jpa.ProjectRepository;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.NameDto;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.project.Issue;
import com.fxlabs.issues.dto.project.IssueStatus;
import com.fxlabs.issues.dto.project.IssueType;
import com.fxlabs.issues.dto.users.Org;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.fxlabs.issues.services.users.OrgService;
import com.fxlabs.issues.services.users.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
@Service
@Transactional
public class IssueServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.project.Issue, Issue, String> implements IssueService {

    private ProjectRepository projectRepository;
    private ProjectConverter projectConverter;
    private IssueRepository repository;
    private IssueESRepository issueESRepository;
    private IssueConverter converter;
    private OrgService orgService;
    private OrgConverter orgConverter;
    private UsersService usersService;




    @Autowired
    public IssueServiceImpl(IssueRepository repository, IssueConverter converter, IssueESRepository issueESRepository, OrgService orgService,
                            ProjectRepository projectRepository, ProjectConverter projectConverter, OrgConverter orgConverter) {

        super(repository, converter);
        this.converter = converter;
        this.issueESRepository = issueESRepository;
        this.repository = repository;
        this.projectConverter = projectConverter;
        this.projectRepository = projectRepository;
        this.orgService = orgService;
        this.orgConverter = orgConverter;
    }


    @Override
    public Response<List<Issue>> findIssues(String projectId, String user, Pageable pageable) {
        Page<com.fxlabs.issues.dao.entity.project.Issue> page = repository.findByProjectId(projectId,  pageable);
        return new Response<List<Issue>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }


    @Override
    public Response<Issue> create(Issue dto, String user, String org) {

        //check project exist with given refid
        //project  exist assign issue to project
        //project dosn't exist create new project in org
        //assign the issue to new project created
        //
        if (StringUtils.isEmpty(dto.getProject().getId()) && StringUtils.isEmpty(dto.getProject().getRefId())){
            Optional<Project> optionalProject = projectRepository.findByRefIdAndInactive(dto.getProject().getRefId(), false);

            if(optionalProject.isPresent()) {
                dto.setProject(projectConverter.convertToDto(optionalProject.get()));
            } else {
                //creating new project
                Project projectEntity = getProject(dto, org);
                dto.setProject(projectConverter.convertToDto(projectEntity));
            }
        }
        //TODO  check issue is not duplicate
        dto.setIssueName("issue_" + dto.getEndpoint() + "_" + dto.getEnv());
        dto.setIssueType(IssueType.AUTOMATIC);
        dto.setIssueStatus(IssueStatus.OPEN);
        Response<Issue> issueResponse = super.save(dto, user);
        issueESRepository.save(converter.convertToEntity(issueResponse.getData()));
        return issueResponse;
    }

    public Response<Issue> createFromUI(Issue dto, String user, String org)
    {
        //TODO validate the user in projectorg

        if (dto == null || dto.getProject() == null || StringUtils.isEmpty(dto.getProject().getId())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for project"));
        }

        dto.setIssueType(IssueType.MANUAL);
        dto.setIssueStatus(IssueStatus.OPEN);
        Response<Issue> issueResponse = super.save(dto, user);
        issueESRepository.save(converter.convertToEntity(issueResponse.getData()));
        return issueResponse;
    }

    private Project getProject(Issue dto, String org) {
        Project projectEntity = new Project();
        projectEntity.setDescription(dto.getProject().getName());
        projectEntity.setDescription(dto.getProject().getDescription());
        projectEntity.setRefId(dto.getProject().getRefId());

        Response<Org> orgResponse = orgService.findById(org);
        NameDto nameDto = new NameDto();
        nameDto.setId(org);
        projectEntity.setOrg(orgConverter.convertToEntity(orgResponse.getData()));
        projectEntity = projectRepository.save(projectEntity);
        return projectEntity;
    }

    @Override
    public Response<Issue> updateFromUI(Issue request, String org, String user) {
        //TODO  check user entitled to org
        if (request == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for update Issue "));
        }

        Optional<com.fxlabs.issues.dao.entity.project.Issue> optionalIssue = repository.findByIdAndProjectId(request.getId(), request.getProject().getId());
        if (!optionalIssue.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null,   String.format("Invavid issue for issue id %s", request.getId())));
        }
        //TODO Apply update rules

        Response<Issue> updatedResponse = this.save(request, user);
        issueESRepository.save(converter.convertToEntity(updatedResponse.getData()));
        return updatedResponse;

    }

    @Override
    public Response<Issue> update(Issue request, String org, String user) {
        //TODO  check user entitled to org
        if (request == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for update Issue "));
        }

        Optional<com.fxlabs.issues.dao.entity.project.Issue> optionalIssue = repository.findByIdAndProjectId(request.getId(), request.getProject().getRefId());
        if (!optionalIssue.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null,   String.format("Invavid issue for issue id %s", request.getId())));
        }

        com.fxlabs.issues.dao.entity.project.Issue issue = optionalIssue.get();
        //
        issue.setAssertions(request.getAssertions());
        issue.setResult(request.getResult());
        issue.setEnv(request.getEnv());
        issue.setFailedAssertions(request.getFailedAssertions());
        issue.setHeaders(request.getHeaders());
        issue.setIssueStatus(com.fxlabs.issues.dao.entity.project.IssueStatus.valueOf(request.getIssueStatus().toString()));
        issue.setMethod(com.fxlabs.issues.dao.entity.project.HttpMethod.valueOf(request.getMethod().toString()));
        issue = repository.save(issue);
        issueESRepository.save(issue);
        //TODO Apply update rules
        return new Response<Issue>(converter.convertToDto(issue));
    }

    @Override
    public Response<Issue> delete(String id, String projectId, String org) {
        //TODO  check user entitled to org
        Optional<com.fxlabs.issues.dao.entity.project.Issue> optionalProject = repository.findByIdAndProjectId(id, projectId);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
        issueESRepository.deleteById(id);
        return delete(id, "");
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }
}
