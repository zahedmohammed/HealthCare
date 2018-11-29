package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.SavingsAccountConverter;
import com.fxlabs.issues.dao.repository.jpa.SavingsAccountRepository;
import com.fxlabs.issues.dto.account.SavingsAccount;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SavingsAccountServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.account.SavingsAccount, SavingsAccount, String> implements SavingsAccountService {

    SavingsAccountRepository savingsAccountRepository;
    SavingsAccountConverter primaryAccountConverter;

    public SavingsAccountServiceImpl(SavingsAccountRepository repository, SavingsAccountConverter converter) {
        super(repository, converter);
        this.primaryAccountConverter = converter;
        this.savingsAccountRepository = repository;
    }

    @Override
    public Response<SavingsAccount> findSavingsAccountById(String id, String currentAuditor) {
        Optional<com.fxlabs.issues.dao.entity.account.SavingsAccount> primaryAccountOptional = savingsAccountRepository.findById(id);
        return new Response<SavingsAccount>(converter.convertToDto(primaryAccountOptional.get()));
    }

    @Override
    public Response<List<SavingsAccount>> findAllSavingsAccount(Integer pageSize, String currentAuditor) {

        Faker faker = new Faker();
        int size;
        if (pageSize > 999) {
            size = faker.random().nextInt(1000, 1500);
        } else {
            size = pageSize;
        }
        List<com.fxlabs.issues.dao.entity.account.SavingsAccount> primaryAccounts = savingsAccountRepository.findAll();
        return (Response<List<SavingsAccount>>) primaryAccountConverter.convertToDtos(primaryAccounts);
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }
}
