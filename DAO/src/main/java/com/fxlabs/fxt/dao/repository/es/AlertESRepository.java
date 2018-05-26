package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.alerts.Alert;
import com.fxlabs.fxt.dao.entity.alerts.AlertStatus;
import com.fxlabs.fxt.dao.entity.alerts.AlertType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface AlertESRepository extends ElasticsearchRepository<Alert, String> {

    public List<Alert> findByRefIdAndOrgId(String refId, String org, Pageable pageable);

    public Optional<Alert> findByIdAndOrgId(String refId, String org);

    public Stream<Alert> findByTypeAndStatus(AlertType type, AlertStatus status);

    public Page<Alert> findByOrgId(String org, Pageable pageable);
}
