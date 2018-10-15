package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.converters.run.TestCaseResponseConverter;
import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TestCaseResponse;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseESRepository;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseITESRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseITRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseRepository;
import com.fxlabs.fxt.dto.it.ITTaskResponse;
import com.fxlabs.fxt.services.alerts.AlertService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
@Component
@Transactional
public class IssueTrackerTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestCaseResponseESRepository testCaseResponseESRepository;

    @Autowired
    private TestCaseResponseRepository testCaseResponseRepository;

    @Autowired
    private TestCaseResponseConverter converter;

    @Autowired
    private TestCaseResponseITRepository testCaseResponseITRepository;

    @Autowired
    private TestCaseResponseITESRepository testCaseResponseITESRepository;

    @Autowired
    private RunRepository runRepository;


    @Autowired
    private AlertService alertService;

    public void process(ITTaskResponse task) {
        try {

            if (task == null || StringUtils.isEmpty(task.getTestCaseResponseId())) {
                logger.info("Invalid IssuerTracker Task response  id [{}]... ", task.getTestCaseResponseId());
                return;
            }


            logger.info("IssuerTracker Task response [{}]...", task.getProjectName());



            Optional<TestCaseResponse> optional = testCaseResponseESRepository.findById(task.getTestCaseResponseId());

            if (optional.get() != null) {
                TestCaseResponse response = optional.get();
                response.setIssueId(task.getIssueId());
                testCaseResponseRepository.save(response);
                testCaseResponseESRepository.save(response);

                String projectId = response.getProjectId();
                String jobId = response.getJobId();
                String testSuite = response.getSuite();
                String testCase = response.getTestCase();

                String id = projectId + "//" + jobId + "//" + testSuite + "//" + testCase;

              //  Optional<TestCaseResponseIssueTracker> existingIssue = testCaseResponseITRepository.findByTestCaseResponseIssueTrackerId(id);
                Optional<TestCaseResponseIssueTracker> existingIssue = testCaseResponseITRepository.findByProjectIdAndJobIdAndTestSuiteNameAndTestCaseNumber(projectId,jobId,testSuite,testCase);

                if (existingIssue.isPresent()) {

                    TestCaseResponseIssueTracker testCaseResponseIssueTracker = existingIssue.get();

                    testCaseResponseIssueTracker.setValidations(testCaseResponseIssueTracker.getValidations() + 1);

                    if ( ! StringUtils.equalsIgnoreCase(testCaseResponseIssueTracker.getStatus(),task.getIssueStatus())) {
                        testCaseResponseIssueTracker.setRunId(task.getRunId());
                        testCaseResponseIssueTracker.setStatus(task.getIssueStatus());
                    }

                    testCaseResponseIssueTracker.setProjectId(response.getProjectId());
                    testCaseResponseIssueTracker.setJobId(response.getJobId());
                    testCaseResponseIssueTracker.setTestSuiteName(response.getSuite());
                    testCaseResponseIssueTracker.setTestCaseNumber(response.getTestCase());

                    testCaseResponseITRepository.save(testCaseResponseIssueTracker);
                    testCaseResponseITESRepository.save(testCaseResponseIssueTracker);

                    updateRun(task.getRunId(), response.getProjectId(), response.getJobId());

                    return;
                }

                TestCaseResponseIssueTracker  newItResponse = new TestCaseResponseIssueTracker();
                newItResponse.setIssueId(task.getIssueId());
                newItResponse.setStatus(task.getIssueStatus());
              //  newItResponse.setTestCaseResponseIssueTrackerId(id);
                newItResponse.setRunId(task.getRunId());

                newItResponse.setProjectId(response.getProjectId());
                newItResponse.setJobId(response.getJobId());
                newItResponse.setTestSuiteName(response.getSuite());
                newItResponse.setTestCaseNumber(response.getTestCase());


                newItResponse.setValidations(1);
                testCaseResponseITRepository.saveAndFlush(newItResponse);
                testCaseResponseITESRepository.save(newItResponse);

                updateRun(task.getRunId(), response.getProjectId(), response.getJobId());
            }
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }

    private void updateRun(String runId, String projectId, String jobId){
        List<TestCaseResponseIssueTracker> itList = testCaseResponseITRepository.findByRunIdAndProjectIdAndJobId(runId, projectId, jobId);

        long newIssues = 0L;
        long closedIssues = 0L;

        for (TestCaseResponseIssueTracker it : itList) {
            if ("open".equalsIgnoreCase(it.getStatus())) {
                newIssues++;
            }
            if ("closed".equalsIgnoreCase(it.getStatus())) {
                closedIssues++;
            }
        }

        Optional<Run> runOptional = runRepository.findById(runId);

        if (runOptional.isPresent()) {
            Run run = runOptional.get();
            run.getTask().setIssuesLogged(newIssues);
            run.getTask().setIssuesClosed(closedIssues);

            //  long totalOpenIssues = testCaseResponseITRepository.countByStatusAndTestCaseResponseIssueTrackerIdLike("open",itId);
            long totalOpenIssues = testCaseResponseITRepository.countByStatusIgnoreCaseAndProjectIdAndJobId("open", run.getJob().getProject().getId(), run.getJob().getId());

            run.getTask().setTotalOpenIssues(totalOpenIssues);
//            run.getStats().put("total_issues", totalOpenIssues);
            runRepository.saveAndFlush(run);
        }

    }
}
