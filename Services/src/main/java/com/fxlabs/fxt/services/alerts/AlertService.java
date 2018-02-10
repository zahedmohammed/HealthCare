package com.fxlabs.fxt.services.alerts;

import com.fxlabs.fxt.dto.alerts.Alert;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface AlertService extends GenericService<Alert, String> {

    public Response<List<Alert>> findRefId(String refId, String user, Pageable pageable);
}
