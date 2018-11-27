package com.fxlabs.issues.services.branch;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.branch.Branch;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface BranchService extends GenericService<Branch, String> {
    Response<Branch> findBranchById(String id, String currentAuditor);

    Response<List<Branch>> findAllBranches(String currentAuditor);
}
