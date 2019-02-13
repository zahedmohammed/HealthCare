package com.fxlabs.issues.services.account;

import com.fxlabs.issues.dto.account.Transfers;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface TransfersService extends GenericService<Transfers, String> {

    Response<Transfers> findTransfersById(String id, String currentAuditor);

    Response<List<Transfers>> findAllTransfers(Integer pageSize, String currentAuditor);

  //  Response<Transfers> findByContactName(String contactName, String currentAuditor);
}
