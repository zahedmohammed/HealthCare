package com.fxlabs.issues.services.transaction;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.transaction.PrimaryTransaction;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface PrimaryTransactionService extends GenericService<PrimaryTransaction, String> {
    Response<PrimaryTransaction> findPrimaryTransactionById(String id, String currentAuditor);

    Response<List<PrimaryTransaction>> findAllPrimaryTransaction(Integer pageSize);
}
