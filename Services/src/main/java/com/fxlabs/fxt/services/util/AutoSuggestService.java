package com.fxlabs.fxt.services.util;

import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.run.TestCaseResponse;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseITRepository;
import com.fxlabs.fxt.dto.project.AutoSuggestion;
import com.fxlabs.fxt.dto.project.AutoSuggestionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AutoSuggestService {

    private TestCaseResponseITRepository testCaseResponseITRepository;
    private TestSuiteESRepository testSuiteESRepository;
    private TestCaseResponseESRepository testCaseResponseESRepository;



    @Autowired
    public AutoSuggestService(TestCaseResponseITRepository testCaseResponseITRepository, TestSuiteESRepository testSuiteESRepository,
                              TestCaseResponseESRepository testCaseResponseESRepository){
        this.testCaseResponseITRepository = testCaseResponseITRepository;
        this.testSuiteESRepository = testSuiteESRepository;
        this.testCaseResponseESRepository = testCaseResponseESRepository;
    }

    public List<AutoSuggestion> getSuggestions(String projectId, String jobId){

        List<AutoSuggestion> suggestions = new ArrayList<>();
        Stream<TestCaseResponseIssueTracker> issues = testCaseResponseITRepository.findByJobIdAndStatusIgnoreCase(jobId,"open");

        List<String> issueIds = new ArrayList<>();
        issues.forEach(issue -> {
            issueIds.add(issue.getIssueId());
        });

        if (CollectionUtils.isEmpty(issueIds)){
            return suggestions;
        }

        Stream<TestCaseResponse> tcResponses = testCaseResponseESRepository.findByJobIdAndIssueIdIn(jobId,issueIds);

        tcResponses.forEach(tcResp -> {

            TestSuite suite = testSuiteESRepository.findByProjectIdAndName(projectId,tcResp.getSuite());

            if (suite != null) {

                AutoSuggestion suggestion = new AutoSuggestion();
                suggestion.setProjectId(projectId);
                suggestion.setEndPoint(tcResp.getEndpointEval());
                suggestion.setRegion(tcResp.getRegion());
                suggestion.setRespStatuCode(tcResp.getStatusCode());

                suggestion.setCategory(suite.getCategory().toString());
                suggestion.setMethod(suite.getMethod().toString());
                suggestion.setTestSuiteName(suite.getName());

                AutoSuggestion suggestion_ = AutoSuggestionUtil.getAutoSuggestion(suite.getCategory().toString());
                suggestion.setEstimates(suggestion_.getEstimates());
                suggestion.setIssueDesc(suggestion_.getIssueDesc());
                suggestion.setSuggestion(suggestion_.getSuggestion());

                suggestions.add(suggestion);
            }

        });


        /*List<String> tsNames = new ArrayList<>();

        issues.forEach(issue -> {
            tsNames.add(issue.getTestSuiteName());
        });

        if (CollectionUtils.isEmpty(tsNames)){
            return suggestions;
        }

        Stream<TestSuite> testSuites = testSuiteESRepository.findByProjectIdAndNameIn(projectId, tsNames);

        testSuites.forEach(testSuite -> {
            AutoSuggestion suggestion = new AutoSuggestion();
            suggestion.setProjectId(projectId);
            suggestion.setCategory(testSuite.getCategory().toString());
            suggestion.setEndPoint(testSuite.getEndpoint());
            suggestion.setMethod(testSuite.getMethod().toString());
            suggestion.setTestSuiteName(testSuite.getName());

            AutoSuggestion suggestion_ = AutoSuggestionUtil.getAutoSuggestion(testSuite.getCategory().toString());
            suggestion.setEstimates(suggestion_.getEstimates());
            suggestion.setIssueDesc(suggestion_.getIssueDesc());
            suggestion.setSuggestion(suggestion_.getSuggestion());

            suggestions.add(suggestion);

        });*/
        return suggestions;
    }


}
