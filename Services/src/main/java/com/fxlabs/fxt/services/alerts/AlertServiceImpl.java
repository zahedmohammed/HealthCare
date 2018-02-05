package com.fxlabs.fxt.services.alerts;

import com.fxlabs.fxt.converters.alerts.AlertConverter;
import com.fxlabs.fxt.dao.repository.es.AlertESRepository;
import com.fxlabs.fxt.dao.repository.jpa.AlertRepository;
import com.fxlabs.fxt.dto.alerts.Alert;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Response<List<Alert>> findRefId(String refId, String user) {
        List<com.fxlabs.fxt.dao.entity.alerts.Alert> alertList = alertESRepository.findByRefIdAndUsersIn(refId, user);
        return new Response<List<Alert>>(converter.convertToDtos(alertList));
    }


}
