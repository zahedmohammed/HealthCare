package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.RecentTransactionConverter;
import com.fxlabs.issues.dao.entity.users.Users;
import com.fxlabs.issues.dto.account.RecentTransaction;
import com.fxlabs.issues.dao.repository.jpa.RecentTransactionRepository;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class RecentTransactionServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.account.RecentTransaction, RecentTransaction, String> implements RecentTransactionService {

    private RecentTransactionConverter recentTransactionConverter;
    private RecentTransactionRepository recentTransactionRepository;

    public RecentTransactionServiceImpl(RecentTransactionRepository recentTransactionRepository, RecentTransactionConverter recentTransactionConverter) {
        super(recentTransactionRepository, recentTransactionConverter);
        this.recentTransactionConverter = recentTransactionConverter;
        this.recentTransactionRepository = recentTransactionRepository;
    }

    @Override
    public Response<RecentTransaction> findRecentTransactionById(String id, String currentAuditor) {

        //Todo - isuserentitled
        Optional<com.fxlabs.issues.dao.entity.account.RecentTransaction> optionalAppointment = recentTransactionRepository.findById(id);
        if (!optionalAppointment.isPresent())
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for appointment"));
        return new Response<RecentTransaction>(recentTransactionConverter.convertToDto(optionalAppointment.get()));

    }

    @Override
    public Response<List<RecentTransaction>> findAllRecentTransactions(Integer pageSize, String currentAuditor) {
        List<com.fxlabs.issues.dao.entity.account.RecentTransaction> appointmentList = new ArrayList<>();
        Faker faker = new Faker();
        int size;
        if (pageSize > 999) {
            size = faker.random().nextInt(1000, 1500);
        } else {
            size = pageSize;
        }

        for (int i = 0; i < size; i++) {
            appointmentList.add(i, data());
            System.out.println("Check Data ----" + appointmentList.toString());
        }

        return new Response<List<RecentTransaction>>(recentTransactionConverter.convertToDtos(appointmentList));
    }

//    @Override
//    public Response<RecentTransaction> findByContactName(String contactName, String currentAuditor) {
//        Optional<com.fxlabs.issues.dao.entity.account.RecentTransaction> optionalAppointment = recentTransactionRepository.findByContactName(contactName);
//        if (!optionalAppointment.isPresent())
//            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for recent transactions"));
//        return new Response<RecentTransaction>(recentTransactionConverter.convertToDto(optionalAppointment.get()));
//     }

    private com.fxlabs.issues.dao.entity.account.RecentTransaction data() {

        com.fxlabs.issues.dao.entity.account.RecentTransaction recentTransactionData = new com.fxlabs.issues.dao.entity.account.RecentTransaction();
        Faker faker = new Faker();
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String location = String.valueOf(faker.random());
        String description = String.valueOf(faker.random());
        boolean confirmed = Boolean.valueOf(faker.bool().bool()) ;

/*
        appointmentData.setDate(date);
*/
        recentTransactionData.setLocation(location);
        recentTransactionData.setDescription(description);
        recentTransactionData.setConfirmed(confirmed);
        recentTransactionData.setCreatedDate(faker.date().birthday());
        recentTransactionData.setModifiedDate(faker.date().birthday());
        recentTransactionData.setId(faker.idNumber().valid());
        recentTransactionData.setCreatedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        recentTransactionData.setModifiedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));

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
    //    appointmentData.setUser(user);

        return recentTransactionData;
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }
}
