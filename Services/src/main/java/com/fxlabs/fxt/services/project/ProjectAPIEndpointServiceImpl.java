package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectAPIEndpointConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectApiEndpoint;
import com.fxlabs.fxt.dao.repository.ProjectAPIEndpointRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectAPIEndpointServiceImpl extends GenericServiceImpl<ProjectApiEndpoint, com.fxlabs.fxt.dto.project.ProjectAPIEndpoint, String> implements ProjectAPIEndpointService {

    @Autowired
    public ProjectAPIEndpointServiceImpl(ProjectAPIEndpointRepository repository, ProjectAPIEndpointConverter converter) {
        super(repository, converter);
    }

}
