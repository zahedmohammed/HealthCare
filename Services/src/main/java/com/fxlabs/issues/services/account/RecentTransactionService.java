package com.fxlabs.issues.services.account;

import com.fxlabs.issues.dto.account.RecentTransaction;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface RecentTransactionService extends GenericService<RecentTransaction, String> {

    Response<RecentTransaction> findRecentTransactionById(String id, String currentAuditor);

    Response<List<RecentTransaction>> findAllRecentTransactions(Integer pageSize, String currentAuditor);

  //  Response<RecentTransaction> findByContactName(String contactName, String currentAuditor);
}
