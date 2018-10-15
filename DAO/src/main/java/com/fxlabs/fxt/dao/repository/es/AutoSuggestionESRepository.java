package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.AutoSuggestion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mohammed Luqman Shareef
 */
public interface AutoSuggestionESRepository extends ElasticsearchRepository<AutoSuggestion, String> {

    Stream<AutoSuggestion> findByProjectIdAndStatusIn(String projectId, List<String> statuses);
}
