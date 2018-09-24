package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.base.TestSuitesDeletedDto;
import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.dto.project.APICoverageDetails;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface TestSuiteService extends GenericService<TestSuite, String> {


    Response<Long> count(String user, Pageable pageable);
    Response<List<TestSuite>> search(String keyword, String user, Pageable pageable);
    Response<List<TestSuite>> findByProjectId(String id, String user, Pageable pageable);

    public Response<TestSuite> findById(String id, String orgId);

    public Response<TestSuite> create(TestSuite dto, String id);

    public Response<TestSuite> createFromUI(String dto, String projectId, String id);

    public Response<com.fxlabs.fxt.dto.project.TestSuite> update(com.fxlabs.fxt.dto.project.TestSuite testSuite, String user, boolean updateVC);
    public void testSuitesDelete(TestSuitesDeletedDto dto, String user);

    Response<List<TestSuite>> search(String runId, String category, String keyword, String org, String user, Pageable pageable);

    Response<APICoverageDetails> getCoverageByProjectId(String id, String user);

    public Response<com.fxlabs.fxt.dto.project.TestSuite> delete(String id, String user);
}
