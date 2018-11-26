package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.PrimaryAccountConverter;
import com.fxlabs.issues.dao.repository.jpa.PrimaryAccountRepository;
import com.fxlabs.issues.dto.account.PrimaryAccount;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrimaryAccountServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.account.PrimaryAccount, PrimaryAccount, String> implements PrimaryAccountService {

    PrimaryAccountRepository primaryAccountRepository;
    PrimaryAccountConverter primaryAccountConverter;

    public PrimaryAccountServiceImpl(PrimaryAccountRepository repository, PrimaryAccountConverter converter) {
        super(repository, converter);
        this.primaryAccountConverter = converter;
        this.primaryAccountRepository = repository;
    }

    @Override
    public Response<PrimaryAccount> findPrimaryAccountById(String id, String currentAuditor) {
        return null;
    }

    @Override
    public Response<List<PrimaryAccount>> findAllPrimaryAccount(String currentAuditor) {

        List<com.fxlabs.issues.dao.entity.account.PrimaryAccount> primaryAccounts = primaryAccountRepository.findAll();
        return (Response<List<PrimaryAccount>>) primaryAccountConverter.convertToDtos(primaryAccounts);
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }
}
