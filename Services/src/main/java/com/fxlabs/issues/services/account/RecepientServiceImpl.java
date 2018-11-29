package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.RecepientConverter;
import com.fxlabs.issues.dao.entity.users.Users;
import com.fxlabs.issues.dao.repository.jpa.RecepientRepository;
import com.fxlabs.issues.dto.account.Recepient;
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
public class RecepientServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.account.Recepient, Recepient, String> implements RecepientService{

    private RecepientRepository recepientRepository;
    private RecepientConverter recepientConverter;

    public RecepientServiceImpl(RecepientRepository recepientRepository, RecepientConverter recepientConverter){

        super(recepientRepository, recepientConverter);
        this.recepientRepository = recepientRepository;
        this.recepientConverter = recepientConverter;
    }

    @Override
    public Response<Recepient> findRecepientById(String id, String user){

        //Todo - isuserentitled

        Optional<com.fxlabs.issues.dao.entity.account.Recepient> optionalRecepient = recepientRepository.findById(id);
        if(!optionalRecepient.isPresent())
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for recepient"));
        return new Response<Recepient>(recepientConverter.convertToDto(optionalRecepient.get()));
    }

    @Override
    public Response<List<Recepient>> findAllRecepients(Integer pageSize, String user){

        //Todo - isuserentitled

        List<com.fxlabs.issues.dao.entity.account.Recepient> recepientList = new ArrayList<>();
        Faker faker = new Faker();
        int size;
        if (pageSize > 999) {
            size = faker.random().nextInt(1000, 1500);
        } else {
            size = pageSize;
        }

        for (int i = 0; i < size; i++){
            recepientList.add(i, data());
            System.out.println("Check Data ----" + recepientList.toString());
        }
        return new Response<List<Recepient>>(recepientConverter.convertToDtos(recepientList));

    }

    private com.fxlabs.issues.dao.entity.account.Recepient data() {

        com.fxlabs.issues.dao.entity.account.Recepient recepientData = new com.fxlabs.issues.dao.entity.account.Recepient();
        Faker faker = new Faker();
        String name = String.valueOf(faker.random());
        String email = String.valueOf(faker.random());
        String phone = String.valueOf(faker.phoneNumber());
        String accountNumber = String.valueOf(faker.random());
        String description = String.valueOf(faker.random());

        recepientData.setName(name);
        recepientData.setEmail(email);
        recepientData.setPhone(phone);
        recepientData.setAccountNumber(accountNumber);
        recepientData.setDescription(description);
        recepientData.setCreatedDate(faker.date().birthday());
        recepientData.setModifiedDate(faker.date().birthday());
        recepientData.setId(faker.idNumber().valid());
        recepientData.setCreatedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        recepientData.setModifiedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));

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
        recepientData.setUser(user);

        return recepientData;
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }

}
