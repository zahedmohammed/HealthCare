package com.fxlabs.issues.services.transaction;

import com.fxlabs.issues.converters.transaction.PrimaryTransactionConverter;
import com.fxlabs.issues.dao.repository.jpa.PrimaryTransactionRepository;
import com.fxlabs.issues.dto.transaction.PrimaryTransaction;
import com.fxlabs.issues.services.base.GenericServiceImpl;

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
}
