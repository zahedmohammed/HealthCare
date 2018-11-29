package com.fxlabs.issues.services.account;

import com.fxlabs.issues.dto.account.PrimaryAccount;
import com.fxlabs.issues.dto.account.SavingsAccount;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface SavingsAccountService extends GenericService<SavingsAccount, String> {
    Response<SavingsAccount> findSavingsAccountById(String id, String currentAuditor);

    Response<List<SavingsAccount>> findAllSavingsAccount(Integer pageSize,String currentAuditor);
}
