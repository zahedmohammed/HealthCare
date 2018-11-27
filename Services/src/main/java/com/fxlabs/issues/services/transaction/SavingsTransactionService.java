package com.fxlabs.issues.services.transaction;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.transaction.SavingsTransaction;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface SavingsTransactionService extends GenericService<SavingsTransaction, String> {
    Response<SavingsTransaction> findSavingsTransactionById(String id, String currentAuditor);

    Response<List<SavingsTransaction>> findAllSavingsTransaction(String currentAuditor);
}
