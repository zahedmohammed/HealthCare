package com.fxlabs.fxt.services.alerts;

import com.fxlabs.fxt.converters.alerts.AlertConverter;
import com.fxlabs.fxt.dao.repository.es.AlertESRepository;
import com.fxlabs.fxt.dao.repository.jpa.AlertRepository;
import com.fxlabs.fxt.dto.alerts.Alert;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class AlertServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.alerts.Alert, com.fxlabs.fxt.dto.alerts.Alert, String> implements AlertService {

    private AlertRepository alertRepository;
    private AlertESRepository alertESRepository;
    private AlertConverter alertConverter;

    @Autowired
    public AlertServiceImpl(AlertRepository alertRepository, AlertESRepository alertESRepository,
                            AlertConverter alertConverter) {

        super(alertRepository, alertConverter);

        this.alertRepository = alertRepository;
        this.alertESRepository = alertESRepository;
        this.converter = alertConverter;

    }

    public Response<Alert> save(Alert dto) {
        Response<Alert> response = save(dto, null);
        alertESRepository.save(converter.convertToEntity(response.getData()));
        return response;
    }

    @Override
    public Response<List<Alert>> findRefId(String refId, String user, Pageable pageable) {
        List<com.fxlabs.fxt.dao.entity.alerts.Alert> alertList = alertESRepository.findByRefIdAndUsersIn(refId, user, pageable);
        return new Response<List<Alert>>(converter.convertToDtos(alertList));
    }

    @Override
    public Response<List<Alert>> findAll(String user, Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.alerts.Alert> page = alertESRepository.findByUsersIn(user, pageable);
        return new Response<List<Alert>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public void isUserEntitled(String id, String user) {
        // TODO
        Optional<com.fxlabs.fxt.dao.entity.alerts.Alert> optional = alertESRepository.findByIdAndUsersIn(id, user);
        if (!optional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }
    }
}
