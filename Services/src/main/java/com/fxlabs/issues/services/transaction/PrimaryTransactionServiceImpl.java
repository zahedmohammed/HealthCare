package com.fxlabs.issues.services.transaction;

import com.fxlabs.issues.converters.transaction.PrimaryTransactionConverter;
import com.fxlabs.issues.dao.repository.jpa.PrimaryTransactionRepository;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.transaction.PrimaryTransaction;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Response<List<PrimaryTransaction>> findAllPrimaryTransaction(String currentAuditor) {
        List<com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction> primaryTransactionList = primaryTransactionRepository.findAll();
        return (Response<List<PrimaryTransaction>>) converter.convertToDtos(primaryTransactionList);
    }
}
