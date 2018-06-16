package com.fxlabs.fxt.services.alerts;

import com.fxlabs.fxt.converters.alerts.AlertConverter;
import com.fxlabs.fxt.dao.repository.es.AlertESRepository;
import com.fxlabs.fxt.dao.repository.jpa.AlertRepository;
import com.fxlabs.fxt.dto.alerts.Alert;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
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
    public Response<List<Alert>> findRefId(String refId, String org, Pageable pageable) {
        List<com.fxlabs.fxt.dao.entity.alerts.Alert> alertList = alertESRepository.findByRefIdAndOrgId(refId, org, pageable);
        return new Response<List<Alert>>(converter.convertToDtos(alertList));
    }

    @Override
    public Response<Alert> findById(String id, String org) {
        Optional<com.fxlabs.fxt.dao.entity.alerts.Alert> optionalAlert = alertESRepository.findByIdAndOrgId(id, org);
        if (optionalAlert.isPresent()) {
            optionalAlert = alertRepository.findById(id);
        }
        Alert alert = converter.convertToDto(optionalAlert.get());
        alert.setMessage(optionalAlert.get().getMessage());
        return new Response<Alert>(alert);
    }

    @Override
    public Response<List<Alert>> findAll(String org, Pageable pageable) {
        Page<com.fxlabs.fxt.dao.entity.alerts.Alert> page = alertESRepository.findByOrgId(org, pageable);
        return new Response<List<Alert>>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public void isUserEntitled(String id, String user) {
        //TODO check user part of the org.
    }
}
