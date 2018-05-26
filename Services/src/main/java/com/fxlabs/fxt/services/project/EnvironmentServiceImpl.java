package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.EnvironmentConverter;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.repository.jpa.EnvironmentRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class EnvironmentServiceImpl extends GenericServiceImpl<Environment, com.fxlabs.fxt.dto.project.Environment, String> implements EnvironmentService {

    private EnvironmentRepository environmentRepository;
    private ProjectService projectService;

    @Autowired
    public EnvironmentServiceImpl(EnvironmentRepository repository, EnvironmentConverter converter, ProjectService projectService) {
        super(repository, converter);
        this.environmentRepository = repository;
        this.projectService = projectService;
    }


    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Environment>> findByProjectId(String projectId, String user) {
        List<Environment> environments = environmentRepository.findByProjectId(projectId);
        return new Response<>(converter.convertToDtos(environments));
    }

    @Override
    public Response<Long> count(String org, Pageable pageable) {
        // check user has access to project
        // find owned projects org --> projects --> jobs
        // users --> org or users --> projects
        // least - a project should be visible to owner
        Response<List<Project>> projectsResponse = projectService.findProjects(org, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {
            Long count = environmentRepository.countByProjectIdAndInactive(p.getId(), false);
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());

    }

    @Override
    public void isUserEntitled(String id, String user) {
        Optional<Environment> environmentOptional = environmentRepository.findById(id);
        if (!environmentOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }
        projectService.isUserEntitled(environmentOptional.get().getProjectId(), user);
    }
}
