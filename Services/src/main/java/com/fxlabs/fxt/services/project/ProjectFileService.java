package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectFile;
import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.services.base.GenericService;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectFileService extends GenericService<ProjectFile, String> {

    public Response<ProjectFile> saveFromProject(Project dto, String projectId);

    public Response<ProjectFile> saveFromTestSuite(TestSuite dto, String projectId);

    public Response<List<ProjectFile>> findByProjectId(String projectId, String user, org.springframework.data.domain.Pageable pageable);
}
