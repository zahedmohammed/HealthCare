package com.fxlabs.issues.services.account;

import com.fxlabs.issues.dto.account.Recepient;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface RecepientService extends GenericService<Recepient, String> {

    Response<Recepient> findRecepientById(String id, String currentAuditor);

    Response<List<Recepient>> findAllRecepients(String currentAuditor);
}
