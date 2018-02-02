package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.EnvironmentConverter;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.repository.jpa.EnvironmentRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class EnvironmentServiceImpl extends GenericServiceImpl<Environment, com.fxlabs.fxt.dto.project.Environment, String> implements EnvironmentService {

    private EnvironmentRepository environmentRepository;

    @Autowired
    public EnvironmentServiceImpl(EnvironmentRepository repository, EnvironmentConverter converter) {
        super(repository, converter);
        this.environmentRepository = repository;
    }


    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Environment>> findByProjectId(String projectId, String user) {
        List<Environment> environments = environmentRepository.findByProjectId(projectId);
        return new Response<>(converter.convertToDtos(environments));
    }


}
