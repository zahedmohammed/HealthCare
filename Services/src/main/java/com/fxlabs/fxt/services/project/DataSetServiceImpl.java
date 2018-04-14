package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.DataSetConverter;
import com.fxlabs.fxt.dao.entity.project.DataSet;
import com.fxlabs.fxt.dao.repository.es.DataSetESRepository;
import com.fxlabs.fxt.dao.repository.jpa.DataSetRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
@Service
@Transactional
public class DataSetServiceImpl extends GenericServiceImpl<DataSet, com.fxlabs.fxt.dto.project.DataSet, String> implements DataSetService {

    private DataSetESRepository dataSetESRepository;
    private DataSetRepository repository;
    private ProjectFileService projectFileService;
    private ProjectService projectService;
    private ProjectRepository projectRepository;
    private DataSetConverter converter;

    @Autowired
    public DataSetServiceImpl(DataSetConverter converter, DataSetESRepository dataSetESRepository,
                              DataSetRepository repository, ProjectFileService projectFileService, ProjectService projectService, ProjectRepository projectRepository) {
        super(repository, converter);
        this.repository = repository;
        this.dataSetESRepository = dataSetESRepository;
        this.projectFileService = projectFileService;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }



    @Override
    public void isUserEntitled(String id, String user) {
        Optional<DataSet> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }
        projectService.isUserEntitled(optional.get().getProject().getId(), user);
    }
}
