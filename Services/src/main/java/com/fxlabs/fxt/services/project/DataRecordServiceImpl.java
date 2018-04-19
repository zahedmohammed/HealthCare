package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.DataRecordConverter;
import com.fxlabs.fxt.converters.project.TestSuiteConverter;
import com.fxlabs.fxt.dao.entity.project.DataRecord;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.repository.es.DataRecordESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.DataRecordRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Mohammed Shoukath Ali
 */
@Service
@Transactional
public class DataRecordServiceImpl extends GenericServiceImpl<DataRecord, com.fxlabs.fxt.dto.project.DataRecord, String> implements DataRecordService {

    private DataRecordESRepository dataRecordESRepository;
    private DataRecordRepository dataRecordRepository;
    private ProjectFileService projectFileService;
    private ProjectService projectService;
    private ProjectRepository projectRepository;

    @Autowired
    public DataRecordServiceImpl(DataRecordConverter converter, DataRecordESRepository dataRecordESRepository,
                                 DataRecordRepository repository, ProjectFileService projectFileService, ProjectService projectService, ProjectRepository projectRepository) {
        super(repository, converter);
        this.repository = repository;
        this.converter = converter;
        this.dataRecordESRepository = dataRecordESRepository;
        this.projectFileService = projectFileService;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.DataRecord> save(com.fxlabs.fxt.dto.project.DataRecord dataRecord, String user) {

        DataRecord ts = converter.convertToEntity(dataRecord);
        DataRecord entity = ((DataRecordRepository) repository).save(ts);
        dataRecordESRepository.save(entity);
        return new Response<com.fxlabs.fxt.dto.project.DataRecord>(converter.convertToDto(entity));

    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.DataRecord>> save(List<com.fxlabs.fxt.dto.project.DataRecord> dtos, String user) {

        List<DataRecord> entities = converter.convertToEntities(dtos);
        List<DataRecord> entities_ = ((DataRecordRepository) repository).saveAll(entities);
        dataRecordESRepository.saveAll(entities_);
        return new Response<List<com.fxlabs.fxt.dto.project.DataRecord>>(converter.convertToDtos(entities_));

    }



    @Override
    public void isUserEntitled(String id, String user) {
        Optional<DataRecord> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }
        //projectService.isUserEntitled(optional.get().getProject().getId(), user);
    }
}