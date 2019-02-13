package com.fxlabs.issues.services.account;

import com.fxlabs.issues.dto.account.BankAccount;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface BankAccountService extends GenericService<BankAccount, String> {
    Response<BankAccount> findBankAccountById(String id, String currentAuditor);

    Response<List<BankAccount>> findAllBankAccount(Integer pageSize, String currentAuditor);

    Response<Boolean> depositAmount(BankAccount request, String currentAuditor);
    Response<Boolean> withdrawAmount(BankAccount request, String currentAuditor);

    Response<Boolean> deleteBankAccount(String id, String currentAuditor);
}
