package com.fxlabs.issues.services.project;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.project.Issue;
import com.fxlabs.issues.dto.project.Project;
import com.fxlabs.issues.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Mohammed Shoukath Ali
 */
public interface IssueService extends GenericService<Issue, String> {

    Response<Issue> delete(String id, String projectId, String org);

    Response<Issue> create(Issue dto, String user, String org);

    Response<List<Issue>> findIssues(String projectId, String owner, Pageable pageable);

    Response<Issue> update(Issue request, String user, String org);

}
