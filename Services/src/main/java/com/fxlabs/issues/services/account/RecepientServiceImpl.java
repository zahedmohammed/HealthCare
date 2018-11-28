package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.RecepientConverter;
import com.fxlabs.issues.dao.repository.jpa.RecepientRepository;
import com.fxlabs.issues.dto.account.Recepient;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Response<List<Recepient>> findAllRecepients(String user){

        //Todo - isuserentitled

        List<com.fxlabs.issues.dao.entity.account.Recepient> recepientList = recepientRepository.findAll();
        return new Response<List<Recepient>>(recepientConverter.convertToDtos(recepientList));

    }

    @Override
    public void isUserEntitled(String s, String user) {

    }

}
