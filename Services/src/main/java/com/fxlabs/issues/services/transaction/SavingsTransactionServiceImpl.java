package com.fxlabs.issues.services.transaction;

import com.fxlabs.issues.converters.transaction.SavingsTransactionConverter;
import com.fxlabs.issues.dao.entity.users.Users;
import com.fxlabs.issues.dao.repository.jpa.SavingsTransactionRepository;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.transaction.SavingsTransaction;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SavingsTransactionServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.transaction.SavingsTransaction, SavingsTransaction, String> implements SavingsTransactionService {

    SavingsTransactionRepository savingsTransactionRepository;
    SavingsTransactionConverter savingsTransactionConverter;

    public SavingsTransactionServiceImpl(SavingsTransactionRepository repository, SavingsTransactionConverter converter) {
        super(repository, converter);
        this.savingsTransactionRepository = repository;
        this.savingsTransactionConverter = converter;
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }

    @Override
    public Response<SavingsTransaction> findSavingsTransactionById(String id, String currentAuditor) {
        Optional<com.fxlabs.issues.dao.entity.transaction.SavingsTransaction> savingsTransaction = savingsTransactionRepository.findById(id);
        return new Response<SavingsTransaction>(converter.convertToDto(savingsTransaction.get()));
    }

    @Override
    public Response<List<SavingsTransaction>> findAllSavingsTransaction(Integer pageSize,String currentAuditor) {
        List<com.fxlabs.issues.dao.entity.transaction.SavingsTransaction> savingsTransactionList = new ArrayList<>(); //= savingsTransactionRepository.findAll();
        Faker faker = new Faker();
        int size;
        if (pageSize > 999) {
            size = faker.random().nextInt(1000, 1500);
        } else {
            size = pageSize;
        }

        for (int i = 0; i < size; i++) {
            savingsTransactionList.set(i, data());
        }

        return new Response<List<SavingsTransaction>>(converter.convertToDtos(savingsTransactionList));
     }

    public com.fxlabs.issues.dao.entity.transaction.SavingsTransaction data() {
        com.fxlabs.issues.dao.entity.transaction.SavingsTransaction savingsTransaction = new com.fxlabs.issues.dao.entity.transaction.SavingsTransaction();
        Faker faker = new Faker();
        String description = String.valueOf(faker.random());
        String type = String.valueOf(faker.random());
        String status = String.valueOf(faker.random());
        double amount = (Double) Double.valueOf(String.valueOf(faker.number().digits(6)));
        BigDecimal availableBalance = BigDecimal.valueOf((Double) Double.valueOf(String.valueOf(faker.number().numberBetween(0,7))));

        savingsTransaction.setAmount(amount);
        savingsTransaction.setAvailableBalance(availableBalance);
        savingsTransaction.setDescription(description);
        savingsTransaction.setStatus(status);
        savingsTransaction.setType(type);
        savingsTransaction.setCreatedDate(faker.date().birthday());
        savingsTransaction.setModifiedDate(faker.date().birthday());
        savingsTransaction.setId(faker.idNumber().valid());
        savingsTransaction.setCreatedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        savingsTransaction.setModifiedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));

        Users user = new Users();
        user.setAccountNonExpired(faker.bool().bool());
        user.setAccountNonLocked(faker.bool().bool());
        user.setAccountNumber(faker.number().numberBetween(1,10));
        user.setCompany(faker.company().industry());
        user.setEmail(faker.internet().emailAddress());
        user.setLocation(faker.address().fullAddress());
        user.setEnabled(faker.bool().bool());
        user.setJobTitle(faker.job().title());
        user.setName(faker.name().name());
        user.setUsername(faker.name().username());
        user.setCreatedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        user.setId(faker.idNumber().valid());
        user.setCreatedDate(faker.date().birthday());
        user.setModifiedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        user.setModifiedDate(faker.date().birthday());
        savingsTransaction.setUser(user);
        return savingsTransaction;
    }
}
