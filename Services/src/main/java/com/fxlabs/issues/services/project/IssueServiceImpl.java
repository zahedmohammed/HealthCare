package com.fxlabs.issues.services.project;

import com.fxlabs.issues.converters.project.IssueConverter;
import com.fxlabs.issues.converters.project.ProjectConverter;
import com.fxlabs.issues.dao.entity.users.Org;
import com.fxlabs.issues.dao.repository.es.IssueESRepository;
import com.fxlabs.issues.dao.repository.jpa.IssueRepository;
import com.fxlabs.issues.dao.repository.jpa.OrgRepository;
import com.fxlabs.issues.dao.repository.jpa.ProjectRepository;
import com.fxlabs.issues.dao.repository.jpa.UsersRepository;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.NameDto;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.project.Issue;
import com.fxlabs.issues.dto.project.Project;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.fxlabs.issues.services.users.SystemSettingService;
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



    @Autowired
    public IssueServiceImpl(IssueRepository repository, IssueConverter converter, IssueESRepository issueESRepository,
                            ProjectRepository projectRepository, ProjectConverter projectConverter) {

        super(repository, converter);
        this.converter = converter;
        this.issueESRepository = issueESRepository;
        this.repository = repository;
        this.projectConverter = projectConverter;
        this.projectRepository = projectRepository;
    }


    @Override
    public Response<List<Issue>> findIssues(String projectId, String user, Pageable pageable) {
        Page<com.fxlabs.issues.dao.entity.project.Issue> page = repository.findByProjectId(projectId,  pageable);
        return new Response<List<Issue>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }


    @Override
    public Response<Issue> create(Issue dto, String user, String org) {
        //TODO validate the user in projectorg

        if (dto == null || dto.getProject() == null || StringUtils.isEmpty(dto.getProject().getId())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for project"));
        }
        //TODO  check issue is not duplicate
        Response<Issue> issueResponse = super.save(dto, user);
        return issueResponse;
    }

    @Override
    public Response<Issue> update(Issue request, String org, String user) {
        //TODO  check user entitled to org
        if (request == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for update Issue "));
        }

        Optional<com.fxlabs.issues.dao.entity.project.Issue> optionalIssue = repository.findByIdAndProjectId(request.getId(), request.getProject().getId());
        if (!optionalIssue.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null,   String.format("Invavid issue for issue id %s")));
        }
        //TODO Apply update rules

        return this.save(request, user);
    }




    @Override
    public Response<Issue> delete(String id, String projectId, String user) {
        //TODO  check user entitled to org
        Optional<com.fxlabs.issues.dao.entity.project.Issue> optionalProject = repository.findByIdAndProjectId(id, projectId);
        if (!optionalProject.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
        return delete(id, user);
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }
}
