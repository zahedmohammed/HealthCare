package com.fxlabs.fxt.services.util;

import com.fxlabs.fxt.converters.project.AutoSuggestionConverter;
import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeGenerator;
import com.fxlabs.fxt.dao.repository.es.AutoSuggestionESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.AutoCodeConfigRepository;
import com.fxlabs.fxt.dao.repository.jpa.AutoSuggestionRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestCaseResponseITRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.project.AutoSuggestion;
import com.fxlabs.fxt.dto.project.AutoSuggestionUtil;
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
    private AutoCodeConfigRepository autoCodeConfigRepository;

    private AutoSuggestionESRepository autoSuggestionESRepository;
    private AutoSuggestionRepository autoSuggestionRepository;
    private AutoSuggestionConverter converter;

    @Autowired
    public AutoSuggestService(TestCaseResponseITRepository testCaseResponseITRepository, TestSuiteESRepository testSuiteESRepository,
                              TestSuiteRepository testSuiteRepository,
                              AutoCodeConfigRepository autoCodeConfigRepository, AutoSuggestionConverter converter,
                              AutoSuggestionESRepository autoSuggestionESRepository, AutoSuggestionRepository autoSuggestionRepository){
        this.testCaseResponseITRepository = testCaseResponseITRepository;
        this.testSuiteESRepository = testSuiteESRepository;
        this.testSuiteRepository = testSuiteRepository;
        this.autoCodeConfigRepository = autoCodeConfigRepository;
        this.autoSuggestionESRepository = autoSuggestionESRepository;
        this.autoSuggestionRepository = autoSuggestionRepository;

        this.converter = converter;
    }

    public List<AutoSuggestion> getSuggestionsByProject(String projectId){

        List<AutoSuggestion> suggestions = new ArrayList<>();

        List<com.fxlabs.fxt.dao.entity.project.SuggestionStatus> statuses = new ArrayList<>();
        statuses.add(com.fxlabs.fxt.dao.entity.project.SuggestionStatus.NEW);
        Stream<com.fxlabs.fxt.dao.entity.project.AutoSuggestion> sugStream = autoSuggestionRepository.findByProjectIdAndStatusIn(projectId,statuses);

        sugStream.forEach(suggestion -> {
            AutoSuggestion suggestionDto = converter.convertToDto(suggestion);

            AutoSuggestion suggestion_ = AutoSuggestionUtil.getAutoSuggestion(suggestionDto.getCategory());
            if (suggestion_ != null) {
                suggestionDto.setEstimates(suggestion_.getEstimates());
                suggestionDto.setIssueDesc(suggestion_.getIssueDesc());
                suggestionDto.setSuggestion(suggestion_.getSuggestion());
            }
            suggestions.add(suggestionDto);

        });

        return suggestions;
    }

    public Boolean skipSuggestion(String id){

        Optional<com.fxlabs.fxt.dao.entity.project.AutoSuggestion> optional = autoSuggestionRepository.findById(id);
        if (optional.isPresent()){
            com.fxlabs.fxt.dao.entity.project.AutoSuggestion suggestion = optional.get();

            Optional<TestSuite> tsOptional = testSuiteRepository.findByProjectIdAndName(suggestion.getProjectId(),suggestion.getTestSuiteName());

            if (tsOptional.isPresent()){
                TestSuite ts = tsOptional.get();
                ts.setInactive(true);
                TestSuite entity = testSuiteRepository.saveAndFlush(ts);
                if (entity != null && entity.getId() != null) {
                    testSuiteESRepository.save(entity);
                }
            }

            //TODO: 2. Generator add skip entry - update AutoCodeConfig....
            Optional<AutoCodeConfig> config = autoCodeConfigRepository.findByProjectId(suggestion.getProjectId());
            List<AutoCodeGenerator> list =  config.get().getGenerators();
            for (AutoCodeGenerator gen : list){
                String type = gen.getType();
                if (StringUtils.endsWithIgnoreCase(suggestion.getTestSuiteName(),type)){
                    //TODO: get endpoint
//                    new AutoCodeGeneratorMatches().setPathPatterns(endPoint);
//                    gen.getMatches().add()
                }
            }

            Stream<TestCaseResponseIssueTracker> tcResponses = testCaseResponseITRepository.findByProjectIdAndTestSuiteNameAndTestCaseNumber(suggestion.getProjectId(),suggestion.getTestSuiteName(),suggestion.getTestCaseNumber());
            tcResponses.forEach(tcResponse -> {
                tcResponse.setStatus("closed");
                testCaseResponseITRepository.save(tcResponse);
            });
        }
        return false;
    }

}
