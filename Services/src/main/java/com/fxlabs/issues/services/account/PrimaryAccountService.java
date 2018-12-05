package com.fxlabs.issues.services.account;

import com.fxlabs.issues.dto.account.PrimaryAccount;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface PrimaryAccountService extends GenericService<PrimaryAccount, String> {
    Response<PrimaryAccount> findPrimaryAccountById(String id, String currentAuditor);

    Response<List<PrimaryAccount>> findAllPrimaryAccount(Integer pageSize,String currentAuditor);

    Response<Boolean> depositAmount(PrimaryAccount request, String currentAuditor);
    Response<Boolean> withdrawAmount(PrimaryAccount request, String currentAuditor);
}
