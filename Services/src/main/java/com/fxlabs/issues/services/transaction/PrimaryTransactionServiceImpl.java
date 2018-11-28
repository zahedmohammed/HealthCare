package com.fxlabs.issues.services.transaction;

import com.fxlabs.issues.converters.transaction.PrimaryTransactionConverter;
import com.fxlabs.issues.dao.entity.users.Users;
import com.fxlabs.issues.dao.repository.jpa.PrimaryTransactionRepository;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.transaction.PrimaryTransaction;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrimaryTransactionServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction, PrimaryTransaction, String> implements PrimaryTransactionService {

    PrimaryTransactionRepository primaryTransactionRepository;
    PrimaryTransactionConverter primaryTransactionConverter;

    public PrimaryTransactionServiceImpl(PrimaryTransactionRepository repository, PrimaryTransactionConverter converter) {
        super(repository, converter);
        this.primaryTransactionRepository = repository;
        this.primaryTransactionConverter = converter;
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }

    @Override
    public Response<PrimaryTransaction> findPrimaryTransactionById(String id, String currentAuditor) {
        Optional<com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction> primaryTransactionOptional = primaryTransactionRepository.findById(id);
        return new Response<PrimaryTransaction>(converter.convertToDto(primaryTransactionOptional.get()));
    }

    @Override
    public Response<List<PrimaryTransaction>> findAllPrimaryTransaction() {
        List<com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction> primaryTransactionList = new ArrayList<>();
        Faker faker = new Faker();
        String size = String.valueOf(faker.random().nextInt(1, 20));
        for (int i = 0; i < 10; i++) {
            primaryTransactionList.add(i, data());
            System.out.println("check data----" + primaryTransactionList.toString());
        }
        return new Response<List<PrimaryTransaction>>(converter.convertToDtos(primaryTransactionList));
    }

    public com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction data() {
        com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction savingsTransaction = new com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction();
        Faker faker = new Faker();
        String description = String.valueOf(faker.book().genre());
        String type = "Primary";
        String status = String.valueOf(faker.bool().bool());
        double amount = (Double) Double.valueOf(String.valueOf(faker.number().numberBetween(1, 600000)));
        BigDecimal availableBalance = BigDecimal.valueOf((Double) Double.valueOf(String.valueOf(faker.number().numberBetween(0, 600000))));

        savingsTransaction.setAmount(amount);
        savingsTransaction.setAvailableBalance(availableBalance);
        savingsTransaction.setDescription(description);
        savingsTransaction.setStatus(status);
        savingsTransaction.setType(type);
        LocalDate localDate = LocalDate.now();
        LocalDate localDate1 = localDate.plusDays(1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date date1 = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());

        savingsTransaction.setCreatedDate(faker.date().between(date, date1));
        savingsTransaction.setModifiedDate(faker.date().between(date, date1));
        savingsTransaction.setId(faker.idNumber().valid());
        savingsTransaction.setCreatedBy(String.valueOf(faker.number().numberBetween(10, 15)));
        savingsTransaction.setModifiedBy(String.valueOf(faker.number().numberBetween(10, 15)));

        Users user = new Users();
        user.setAccountNonExpired(faker.bool().bool());
        user.setAccountNonLocked(faker.bool().bool());
        user.setAccountNumber((int) faker.number().numberBetween(100000000, 9999999));
        user.setCompany(faker.company().industry());
        user.setEmail(faker.internet().emailAddress());
        user.setLocation(faker.address().fullAddress());
        user.setEnabled(faker.bool().bool());
        user.setJobTitle(faker.job().title());
        user.setName(faker.name().name());
        user.setUsername(faker.name().username());
        user.setCreatedBy(String.valueOf(faker.number().numberBetween(10, 15)));
        user.setId(faker.idNumber().valid());
        user.setCreatedDate(faker.date().between(date, date1));
        user.setModifiedBy(String.valueOf(faker.number().numberBetween(10, 15)));
        user.setModifiedDate(faker.date().between(date, date1));
        savingsTransaction.setUser(user);
        return savingsTransaction;
    }
}
