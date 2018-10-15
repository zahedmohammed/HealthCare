package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.AutoSuggestion;
import com.fxlabs.fxt.dao.entity.project.SuggestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mohammed Luqman Shareef
 */
public interface AutoSuggestionRepository extends JpaRepository<AutoSuggestion, String> {

    Stream<AutoSuggestion> findByProjectIdAndStatusIn(String projectId, List<SuggestionStatus> statuses);

    Stream<AutoSuggestion> findByProjectIdAndTestSuiteNameAndTestCaseNumber(String projectId, String testSuiteName, String testCaseNumber);
}
