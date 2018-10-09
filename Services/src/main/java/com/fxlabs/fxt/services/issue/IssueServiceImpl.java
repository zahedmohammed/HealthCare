package com.fxlabs.fxt.services.issue;

import com.fxlabs.fxt.converters.issues.TestCaseResponseIssueTrackerConverter;
import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseITESRepository;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseITRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IssueServiceImpl extends GenericServiceImpl<TestCaseResponseIssueTracker, com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker, String> implements IssueService {

    private TestCaseResponseITESRepository testCaseResponseITESRepository;
    private TestCaseResponseIssueTrackerConverter testCaseResponseIssueTrackerConverter;
    private JobRepository jobRepository;

    @Autowired
    public IssueServiceImpl(TestCaseResponseITRepository repository, TestCaseResponseIssueTrackerConverter converter,
                            TestCaseResponseIssueTrackerConverter testCaseResponseIssueTrackerConverter, JobRepository jobRepository,
                            TestCaseResponseITESRepository testCaseResponseITESRepository) {
        super(repository, converter);

        this.testCaseResponseITESRepository = testCaseResponseITESRepository;
        this.testCaseResponseIssueTrackerConverter = testCaseResponseIssueTrackerConverter;
        this.jobRepository = jobRepository;
    }

    @Override
    public void isUserEntitled(String id, String user) {
        Optional<TestCaseResponseIssueTracker> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", id));
        }

        if (!org.apache.commons.lang3.StringUtils.equals(optional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }

    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>> findIssuesByJobId(String id, String status, String currentAuditor, Pageable pageable) {
        if (status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed")) {
            Page<TestCaseResponseIssueTracker> page = testCaseResponseITESRepository.findByJobIdAndStatus(id, status, pageable);

            List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker> testCaseResponseIssueTrackers = converter.convertToDtos(page.getContent());

            return new Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>>(testCaseResponseIssueTrackers, page.getTotalElements(), page.getTotalPages());


        } else {
            Page<TestCaseResponseIssueTracker> page = testCaseResponseITESRepository.findByJobId(id, pageable);

            List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker> testCaseResponseIssueTrackers = converter.convertToDtos(page.getContent());

            return new Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>>(testCaseResponseIssueTrackers, page.getTotalElements(), page.getTotalPages());

        }

    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>> findIssuesByEnvId(String id, String status, String currentAuditor, Pageable pageable) {

        List<Job> jobs = jobRepository.findByEnvironmentIdAndInactive(id, false);

        List<String> jobIds = new ArrayList<>();
        jobs.stream().forEach(j -> {
            jobIds.add(j.getId());
        });
        jobIds.removeAll(Arrays.asList("", null));
        logger.info("jobIds in array {}", jobIds);
        if (status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed")) {

            Page<TestCaseResponseIssueTracker> page = testCaseResponseITESRepository.findByJobIdInAndStatus(jobIds, status, pageable);

            List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker> testCaseResponseIssueTrackers = converter.convertToDtos(page.getContent());

            return new Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>>(testCaseResponseIssueTrackers, page.getTotalElements(), page.getTotalPages());

        } else {
            Page<TestCaseResponseIssueTracker> page = testCaseResponseITESRepository.findByJobIdIn(jobIds, pageable);

            List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker> testCaseResponseIssueTrackers = converter.convertToDtos(page.getContent());

            return new Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>>(testCaseResponseIssueTrackers, page.getTotalElements(), page.getTotalPages());

        }

    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>> findIssuesByProjectId(String id, String status, String currentAuditor, Pageable pageable) {
        if (status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed")) {
            Page<TestCaseResponseIssueTracker> page = testCaseResponseITESRepository.findByProjectIdAndStatus(id, status, pageable);

            List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker> testCaseResponseIssueTrackers = converter.convertToDtos(page.getContent());

            return new Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>>(testCaseResponseIssueTrackers, page.getTotalElements(), page.getTotalPages());

        } else {
            Page<TestCaseResponseIssueTracker> page = testCaseResponseITESRepository.findByProjectId(id, pageable);

            List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker> testCaseResponseIssueTrackers = converter.convertToDtos(page.getContent());

            return new Response<List<com.fxlabs.fxt.dto.it.TestCaseResponseIssueTracker>>(testCaseResponseIssueTrackers, page.getTotalElements(), page.getTotalPages());

        }
    }
}
