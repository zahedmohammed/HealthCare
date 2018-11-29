package com.fxlabs.issues.services.transaction;

import com.fxlabs.issues.dao.entity.users.Users;

import com.fxlabs.issues.dto.transaction.PrimaryTransaction;
import com.github.javafaker.Faker;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mohammed Shoukath Ali
 */
@Service
public class DummyDataProcessor {

    private static List<PrimaryTransaction> primaryTransactionListDto = null;

    @PostConstruct
    public void findAllPrimaryTransaction() {
        int pageSize = 1200;
        System.out.print("Primary Transactions construct...........");
        List<PrimaryTransaction> primaryTransactionList = new ArrayList<>();
        Faker faker = new Faker();

        int size;
        if (pageSize > 999) {
            size = faker.random().nextInt(1000, 1500);
        } else {
            size = pageSize;
        }

        for (int i = 0; i < size; i++) {
            primaryTransactionList.add(i, data());
          //  System.out.println("check data----" + primaryTransactionList.toString());
        }
        primaryTransactionListDto = primaryTransactionList;

                System.out.print("Primary Transactions dummy data processing complete...........");

    }

    public List<PrimaryTransaction> getList(int size){

        if (CollectionUtils.isNotEmpty(primaryTransactionListDto)) {
            if (size > primaryTransactionListDto.size()) {
                size = primaryTransactionListDto.size();
            }
            System.out.print("Loading......");
            List<PrimaryTransaction> firstNElementsList = primaryTransactionListDto.stream().limit(size).collect(Collectors.toList());
            return firstNElementsList;
        }
        return null;
    }

    @Async
    public PrimaryTransaction data() {
        PrimaryTransaction savingsTransaction = new PrimaryTransaction();
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
       // savingsTransaction.setUser(user);
        return savingsTransaction;
    }

}