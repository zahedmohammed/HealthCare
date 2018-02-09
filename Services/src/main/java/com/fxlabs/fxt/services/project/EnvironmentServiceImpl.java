package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.EnvironmentConverter;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.repository.jpa.EnvironmentRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public void isUserEntitled(String id, String user) {
        Optional<Environment> environmentOptional = environmentRepository.findById(id);
        if (!environmentOptional.isPresent()) {
            throw new FxException(String.format("Invalid job id [%s]", id));
        }
        projectService.isUserEntitled(environmentOptional.get().getProjectId(), user);
    }
}
