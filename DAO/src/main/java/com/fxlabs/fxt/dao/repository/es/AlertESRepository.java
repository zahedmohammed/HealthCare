package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.alerts.Alert;
import com.fxlabs.fxt.dao.entity.alerts.AlertSeverity;
import com.fxlabs.fxt.dao.entity.alerts.AlertStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface AlertESRepository extends ElasticsearchRepository<Alert, String> {

    public List<Alert> findByRefIdAndUsersIn(String refId, String user);

    public Stream<Alert> findBySeverityAndStatus(AlertSeverity severity, AlertStatus status);
}
