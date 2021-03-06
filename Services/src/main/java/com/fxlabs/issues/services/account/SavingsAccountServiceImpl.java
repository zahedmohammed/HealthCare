package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.SavingsAccountConverter;
import com.fxlabs.issues.dao.repository.jpa.SavingsAccountRepository;
import com.fxlabs.issues.dto.account.SavingsAccount;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SavingsAccountServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.account.SavingsAccount, SavingsAccount, String> implements SavingsAccountService {

    SavingsAccountRepository savingsAccountRepository;
    SavingsAccountConverter savingsAccountConverter;

    public SavingsAccountServiceImpl(SavingsAccountRepository repository, SavingsAccountConverter converter) {
        super(repository, converter);
        this.savingsAccountConverter = converter;
        this.savingsAccountRepository = repository;
    }

    @Override
    public Response<SavingsAccount> findSavingsAccountById(String id, String currentAuditor) {
        Optional<com.fxlabs.issues.dao.entity.account.SavingsAccount> savingAccountOptional = savingsAccountRepository.findById(id);
//        Optional<com.fxlabs.issues.dao.entity.account.SavingsAccount> savingAccountOptional1 = savingsAccountRepository.findByIdAndCurrentAuditor(id);
//        if(savingAccountOptional.isPresent() && savingAccountOptional1.isPresent())
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "You are not Authorized to view this account"));

        return new Response<SavingsAccount>(converter.convertToDto(savingAccountOptional.get()));
    }

    @Override
    public Response<List<SavingsAccount>> findAllSavingsAccount(Integer pageSize, String currentAuditor) {
//        List<com.fxlabs.issues.dao.entity.account.SavingsAccount> savingAccounts = new ArrayList<>();

        Faker faker = new Faker();
        int size;
        if (pageSize > 999) {
            size = faker.random().nextInt(1000, 1500);
        } else {
            size = pageSize;
        }
      List<com.fxlabs.issues.dao.entity.account.SavingsAccount> savingAccounts = savingsAccountRepository.findAll();
        return new Response<List<SavingsAccount>> (savingsAccountConverter.convertToDtos(savingAccounts));
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }
}
