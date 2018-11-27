package com.fxlabs.issues.services.transaction;

import com.fxlabs.issues.converters.transaction.SavingsTransactionConverter;
import com.fxlabs.issues.dao.repository.jpa.SavingsTransactionRepository;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.transaction.SavingsTransaction;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Response<List<SavingsTransaction>> findAllSavingsTransaction(String currentAuditor) {
        List<com.fxlabs.issues.dao.entity.transaction.SavingsTransaction> savingsTransactionList = savingsTransactionRepository.findAll();
        return (Response<List<SavingsTransaction>>) converter.convertToDtos(savingsTransactionList);
    }
}
