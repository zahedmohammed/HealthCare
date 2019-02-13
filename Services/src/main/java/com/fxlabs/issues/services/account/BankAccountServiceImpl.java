package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.BankAccountConverter;
import com.fxlabs.issues.dao.repository.jpa.BankAccountRepository;
import com.fxlabs.issues.dto.account.BankAccount;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankAccountServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.account.BankAccount, BankAccount, String> implements BankAccountService {

    BankAccountRepository bankAccountRepository;
    BankAccountConverter bankAccountConverter;

    public BankAccountServiceImpl(BankAccountRepository repository, BankAccountConverter converter) {
        super(repository, converter);
        this.bankAccountConverter = converter;
        this.bankAccountRepository = repository;
    }

    @Override
    public Response<BankAccount> findBankAccountById(String id, String currentAuditor) {

        Optional<com.fxlabs.issues.dao.entity.account.BankAccount> bankAccountOptional = bankAccountRepository.findByIdAndCreatedBy(id, currentAuditor);
        Optional<com.fxlabs.issues.dao.entity.account.BankAccount> bankAccountOptional1 = bankAccountRepository.findById(id);
        if (bankAccountOptional1.isPresent() && !bankAccountOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "UnAuthorized request for Account"));
        }
        return new Response<BankAccount>(converter.convertToDto(bankAccountOptional.get()));
    }

    @Override
    public Response<List<BankAccount>> findAllBankAccount(Integer pageSize, String currentAuditor) {

        Faker faker = new Faker();

       /* int size;
        if (pageSize > 999) {
            size = faker.random().nextInt(1000, 1500);
        } else {
            size = pageSize;
        }*/
        List<com.fxlabs.issues.dao.entity.account.BankAccount> bankAccounts = bankAccountRepository.findAll();


        if (bankAccounts.isEmpty()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "No accounts found"));
        } else {
            List<BankAccount> accountList = converter.convertToDtos(bankAccounts);

            return new Response<>(accountList);

        }
    }

    @Override
    public Response<Boolean> depositAmount(BankAccount request, String currentAuditor) {

        Optional<com.fxlabs.issues.dao.entity.account.BankAccount> account = null;
//        if(request.getAccountBalance() > 9 ){
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null,"invalid account number"));
//        }

        if (!request.getId().isEmpty() || request.getId() != null) {
            account = bankAccountRepository.findById(request.getId());

//            if (request.getAccountType().toString().equalsIgnoreCase("saving") ||
//                    request.getAccountType().toString().equalsIgnoreCase("current")) {

                Integer presentAmt = Integer.valueOf(String.valueOf(account.get().getAccountBalance()));
                Integer newAmount = Integer.valueOf(String.valueOf(request.getAccountBalance())) + presentAmt;
                System.out.println(presentAmt);
                account.get().setAccountBalance(Integer.valueOf(newAmount));

//            }
//            else
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null,"invalid account type"));
        }
        bankAccountRepository.save(account.get());

        return new Response<>(true);
    }

    @Override
    public Response<Boolean> withdrawAmount(BankAccount request, String currentAuditor) {
        Optional<com.fxlabs.issues.dao.entity.account.BankAccount> account = null;

//        if(request.getAccountBalance() == null ){
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null,"invalid amount"));
//        }
        if (!request.getId().isEmpty() || request.getId() != null) {
            account = bankAccountRepository.findById(request.getId());

            Integer presentAmt = Integer.valueOf(String.valueOf(account.get().getAccountBalance()));
            Integer newAmount = presentAmt - Integer.valueOf(String.valueOf(request.getAccountBalance()));
            System.out.println(presentAmt);
            account.get().setAccountBalance(Integer.valueOf(newAmount));

        }

        bankAccountRepository.save(account.get());

        return new Response<>(true);
    }

    @Override
    public Response<Boolean> deleteBankAccount(String id, String currentAuditor) {
        Optional<com.fxlabs.issues.dao.entity.account.BankAccount> bankAccountOptional1 = bankAccountRepository.findById(id);
        if (!currentAuditor.equalsIgnoreCase(bankAccountOptional1.get().getCreatedBy())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "UnAuthorized request for Delete Account"));
        } else {
            Optional<com.fxlabs.issues.dao.entity.account.BankAccount> bankAccountOptional = bankAccountRepository.deleteByIdAndCreatedBy(id, currentAuditor);
            System.out.println("check account " + bankAccountOptional);
            // return new Response<PrimaryAccount>(converter.convertToDto(primaryAccountOptional.get()));
            return new Response<>(true);

        }
    }


    @Override
    public void isUserEntitled(String s, String user) {

    }
}
