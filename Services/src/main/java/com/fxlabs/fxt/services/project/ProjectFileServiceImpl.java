package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.ProjectFileConverter;
import com.fxlabs.fxt.dao.repository.es.ProjectFileESRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectFileRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.DataSet;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectFile;
import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class ProjectFileServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.project.ProjectFile, ProjectFile, String> implements ProjectFileService {

    private ProjectFileESRepository projectFileESRepository;
    private ProjectFileRepository projectFileRepository;

    @Autowired
    public ProjectFileServiceImpl(ProjectFileRepository repository, ProjectFileConverter converter, ProjectFileESRepository projectFileESRepository) {
        super(repository, converter);
        this.projectFileESRepository = projectFileESRepository;
        this.projectFileRepository = repository;
    }

    @Override
    public Response<ProjectFile> saveFromProject(Project dto, String projectId) {

        // set org

        if (dto.getProps().isEmpty()) {
            return new Response<>();
        }

        // create project_file
        String fileName = dto.getProps().get(Project.FILE_NAME);
        String content = dto.getProps().get(Project.FILE_CONTENT);
        String modified = dto.getProps().get(Project.MODIFIED_DATE);
        String md5Hex = dto.getProps().get(Project.MD5_HEX);


        return saveProjectFile(projectId, fileName, content, modified, md5Hex);
    }

    @Override
    public Response<ProjectFile> saveFromTestSuite(TestSuite dto, String projectId) {

        // set org

        if (dto.getProps().isEmpty()) {
            return new Response<>();
        }

        // create project_file

        String fileName = dto.getProps().get(Project.FILE_NAME);
        String content = dto.getProps().get(Project.FILE_CONTENT);
        String modified = dto.getProps().get(Project.MODIFIED_DATE);
        String md5Hex = dto.getProps().get(Project.MD5_HEX);

        return saveProjectFile(projectId, fileName, content, modified, md5Hex);
    }

    @Override
    public Response<ProjectFile> saveFromDataSet(DataSet dto, String projectId) {

        // set org

        if (dto.getProps().isEmpty()) {
            return new Response<>();
        }

        // create project_file

        String fileName = dto.getProps().get(Project.FILE_NAME);
        String content = dto.getProps().get(Project.FILE_CONTENT);
        String modified = dto.getProps().get(Project.MODIFIED_DATE);
        String md5Hex = dto.getProps().get(Project.MD5_HEX);

        return saveProjectFile(projectId, fileName, content, modified, md5Hex);
    }

    @Override
    public Response<List<ProjectFile>> findByProjectId(String projectId, String org, org.springframework.data.domain.Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.project.ProjectFile> page = this.projectFileESRepository.findByProjectId(projectId, pageable);
        return new Response<>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<ProjectFile>> findAllFilesByProjectId(String projectId, String org) {
        List<com.fxlabs.fxt.dao.entity.project.ProjectFile> list = this.projectFileRepository.findByProjectId(projectId);
        return new Response<>(converter.convertToDtos(list));
    }

    private Response<ProjectFile> saveProjectFile(String projectId, String fileName, String content, String modified, String md5Hex) {
        Optional<com.fxlabs.fxt.dao.entity.project.ProjectFile> projectFileOptional = projectFileESRepository.findByProjectIdAndFilenameIgnoreCase(projectId, fileName);
        com.fxlabs.fxt.dao.entity.project.ProjectFile projectFile = null;
        if (projectFileOptional.isPresent()) {
            projectFile = projectFileOptional.get();
        } else {
            projectFile = new com.fxlabs.fxt.dao.entity.project.ProjectFile();
            projectFile.setFilename(fileName);
            //com.fxlabs.fxt.dao.entity.project.Project project = new com.fxlabs.fxt.dao.entity.project.Project();
            //project.setId(projectId);
            projectFile.setProjectId(projectId);
        }

        projectFile.setContent(content);
        projectFile.setModified(new Date(Long.parseLong(modified)));
        projectFile.setChecksum(md5Hex);

        Response<ProjectFile> projectFileResponse = save(converter.convertToDto(projectFile));
        projectFileESRepository.save(converter.convertToEntity(projectFileResponse.getData()));


        return projectFileResponse;
    }

    public Response<ProjectFile> findByProjectIdAndFilename(String projectId, String fileName){

        Optional<com.fxlabs.fxt.dao.entity.project.ProjectFile> projectFileOptional = projectFileESRepository.findByProjectIdAndFilenameIgnoreCase(projectId, fileName);
        com.fxlabs.fxt.dao.entity.project.ProjectFile projectFile = null;
        if (projectFileOptional.isPresent()) {
            projectFile = projectFileOptional.get();
            return new Response<>(converter.convertToDto(projectFile));
        }
        return null;
    }

    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }
}
