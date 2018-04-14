package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.alerts.Alert;
import com.fxlabs.fxt.dao.entity.alerts.AlertType;
import com.fxlabs.fxt.dao.entity.alerts.AlertStatus;
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

    public List<Alert> findByRefIdAndUsersIn(String refId, String user, Pageable pageable);

    public Optional<Alert> findByIdAndUsersIn(String refId, String user);

    public Stream<Alert> findByTypeAndStatus(AlertType type, AlertStatus status);

    public Page<Alert> findByUsersIn(String user, Pageable pageable);
}
