package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.PrimaryAccountConverter;
import com.fxlabs.issues.dao.repository.jpa.PrimaryAccountRepository;
import com.fxlabs.issues.dto.account.PrimaryAccount;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

        Optional<com.fxlabs.issues.dao.entity.account.PrimaryAccount> primaryAccountOptional = primaryAccountRepository.findByIdAndCreatedBy(id, currentAuditor);
        Optional<com.fxlabs.issues.dao.entity.account.PrimaryAccount> primaryAccountOptional1 = primaryAccountRepository.findById(id);
        if (primaryAccountOptional1.isPresent() && !primaryAccountOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "UnAuthorized request for Account"));
        }
        return new Response<PrimaryAccount>(converter.convertToDto(primaryAccountOptional.get()));
    }

    @Override
    public Response<List<PrimaryAccount>> findAllPrimaryAccount(Integer pageSize, String currentAuditor) {

        Faker faker = new Faker();

       /* int size;
        if (pageSize > 999) {
            size = faker.random().nextInt(1000, 1500);
        } else {
            size = pageSize;
        }*/
        List<com.fxlabs.issues.dao.entity.account.PrimaryAccount> primaryAccounts = primaryAccountRepository.findAll();


        if (primaryAccounts.isEmpty()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "No accounts found"));
        } else {
            List<com.fxlabs.issues.dto.account.PrimaryAccount> accountList = converter.convertToDtos(primaryAccounts);

            return new Response<>(accountList);

        }
    }

    @Override
    public Response<Boolean> depositAmount(PrimaryAccount request, String currentAuditor) {

        Optional<com.fxlabs.issues.dao.entity.account.PrimaryAccount> account = null;
//        if(request.getAccountBalance() > 9 ){
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null,"invalid account number"));
//        }
        if (!request.getId().isEmpty() || request.getId() != null) {
            account = primaryAccountRepository.findById(request.getId());

            Integer presentAmt = Integer.valueOf(String.valueOf(account.get().getAccountBalance()));
            Integer newAmount = Integer.valueOf(String.valueOf(request.getAccountBalance())) + presentAmt;
            System.out.println(presentAmt);
            account.get().setAccountBalance(BigDecimal.valueOf(newAmount));

        }

        primaryAccountRepository.save(account.get());

        return new Response<>(true);
    }

    @Override
    public Response<Boolean> withdrawAmount(PrimaryAccount request, String currentAuditor) {
        Optional<com.fxlabs.issues.dao.entity.account.PrimaryAccount> account = null;

//        if(request.getAccountBalance() == null ){
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null,"invalid amount"));
//        }
        if (!request.getId().isEmpty() || request.getId() != null) {
            account = primaryAccountRepository.findById(request.getId());

            Integer presentAmt = Integer.valueOf(String.valueOf(account.get().getAccountBalance()));
            Integer newAmount = presentAmt - Integer.valueOf(String.valueOf(request.getAccountBalance()));
            System.out.println(presentAmt);
            account.get().setAccountBalance(BigDecimal.valueOf(newAmount));

        }

        primaryAccountRepository.save(account.get());

        return new Response<>(true);
    }

    @Override
    public Response<Boolean> deletePrimaryAccount(String id, String currentAuditor) {
        Optional<com.fxlabs.issues.dao.entity.account.PrimaryAccount> primaryAccountOptional1 = primaryAccountRepository.findById(id);
        if (!currentAuditor.equalsIgnoreCase(primaryAccountOptional1.get().getCreatedBy())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "UnAuthorized request for Delete Account"));
        } else {
            Optional<com.fxlabs.issues.dao.entity.account.PrimaryAccount> primaryAccountOptional = primaryAccountRepository.deleteByIdAndCreatedBy(id, currentAuditor);
            System.out.println("check account " + primaryAccountOptional);
            // return new Response<PrimaryAccount>(converter.convertToDto(primaryAccountOptional.get()));
            return new Response<>(true);

        }
    }


    @Override
    public void isUserEntitled(String s, String user) {

    }
}
