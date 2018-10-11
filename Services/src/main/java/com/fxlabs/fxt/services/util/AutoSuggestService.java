package com.fxlabs.fxt.services.util;

import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeGenerator;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeGeneratorMatches;
import com.fxlabs.fxt.dao.entity.run.TestCaseResponse;
import com.fxlabs.fxt.dao.repository.es.TestCaseResponseESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.AutoCodeConfigRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseITRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.AutoSuggestion;
import com.fxlabs.fxt.dto.project.AutoSuggestionUtil;
import com.fxlabs.fxt.services.project.TestSuiteService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AutoSuggestService {

    private TestCaseResponseITRepository testCaseResponseITRepository;

    private TestSuiteESRepository testSuiteESRepository;
    private TestSuiteRepository testSuiteRepository;
    private TestCaseResponseESRepository testCaseResponseESRepository;
    private AutoCodeConfigRepository autoCodeConfigRepository;

    @Autowired
    public AutoSuggestService(TestCaseResponseITRepository testCaseResponseITRepository, TestSuiteESRepository testSuiteESRepository,
                              TestCaseResponseESRepository testCaseResponseESRepository, TestSuiteRepository testSuiteRepository,
                              AutoCodeConfigRepository autoCodeConfigRepository){
        this.testCaseResponseITRepository = testCaseResponseITRepository;
        this.testSuiteESRepository = testSuiteESRepository;
        this.testCaseResponseESRepository = testCaseResponseESRepository;
        this.testSuiteRepository = testSuiteRepository;
        this.autoCodeConfigRepository = autoCodeConfigRepository;
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
                suggestion.setRespStatusCode(tcResp.getStatusCode());
                suggestion.setCategory(suite.getCategory().toString());
                suggestion.setMethod(suite.getMethod().toString());
                suggestion.setTestSuiteName(suite.getName());
                suggestion.setTestCaseNumber(tcResp.getTestCase());
                suggestion.setCreatedDate(tcResp.getCreatedDate());

                AutoSuggestion suggestion_ = AutoSuggestionUtil.getAutoSuggestion(suite.getCategory().toString());
                if (suggestion_ != null) {
                    suggestion.setEstimates(suggestion_.getEstimates());
                    suggestion.setIssueDesc(suggestion_.getIssueDesc());
                    suggestion.setSuggestion(suggestion_.getSuggestion());
                }
                suggestions.add(suggestion);
            }
        });
        return suggestions;
    }


    public Boolean skipSuggestion(String projectId, String jobId,  String testSuiteName, String tcNumber, String user){

        Optional<TestCaseResponseIssueTracker> tcResponse = testCaseResponseITRepository.findByProjectIdAndJobIdAndTestSuiteNameAndTestCaseNumber(projectId,jobId,testSuiteName,tcNumber);

        if (tcResponse.isPresent()){

            // 1. Delete TestSuite
            // 2. Generator add skip entry
            // 3. Close issue

            Optional<TestSuite> tsOptional = testSuiteRepository.findByProjectIdAndName(projectId,testSuiteName);

            if (tsOptional.isPresent()){
                TestSuite ts = tsOptional.get();
                ts.setInactive(true);
                TestSuite entity = testSuiteRepository.saveAndFlush(ts);
                if (entity != null && entity.getId() != null) {
                    testSuiteESRepository.save(entity);
                }
            }

            //TODO: 2. Generator add skip entry - update AutoCodeConfig....

            Optional<AutoCodeConfig> config = autoCodeConfigRepository.findByProjectId(projectId);
            List<AutoCodeGenerator> list =  config.get().getGenerators();
            for (AutoCodeGenerator gen : list){
                String type = gen.getType();
                if (StringUtils.endsWithIgnoreCase(testSuiteName,type)){
                    //TODO: get endpoint
//                    new AutoCodeGeneratorMatches().setPathPatterns(endPoint);
//                    gen.getMatches().add()
                }

            }


            TestCaseResponseIssueTracker tcit = tcResponse.get();
            tcit.setStatus("closed");
            testCaseResponseITRepository.save(tcit);
        }

        return false;
    }

}
