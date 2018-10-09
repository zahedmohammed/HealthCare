package com.fxlabs.fxt.services.issue;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IssueService extends GenericService<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker, String> {
    Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>> findIssuesByJobId(String id, String status, String currentAuditor, Pageable pageable);

    Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>> findIssuesByEnvId(String id, String status, String currentAuditor, Pageable pageable);

    Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>> findIssuesByProjectId(String id, String status, String currentAuditor, Pageable pageable);
}
