package com.fxlabs.issues.services.branch;

import com.fxlabs.issues.converters.account.PrimaryAccountConverter;
import com.fxlabs.issues.converters.branch.BranchConverter;
import com.fxlabs.issues.dao.repository.jpa.BranchRepository;
import com.fxlabs.issues.dao.repository.jpa.PrimaryAccountRepository;
import com.fxlabs.issues.dto.account.PrimaryAccount;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.branch.Branch;
import com.fxlabs.issues.services.account.PrimaryAccountService;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BranchServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.branch.Branch, Branch, String> implements BranchService {

    BranchRepository branchRepository;
    BranchConverter branchConverter;

    public BranchServiceImpl(BranchRepository repository, BranchConverter converter) {
        super(repository, converter);
        this.branchConverter = converter;
        this.branchRepository = repository;
    }

    @Override
    public Response<Branch> findBranchById(String id, String currentAuditor) {
        Optional<com.fxlabs.issues.dao.entity.branch.Branch> branch = branchRepository.findById(id);
        return new Response<Branch>(converter.convertToDto(branch.get()));
    }

    @Override
    public Response<List<Branch>> findAllBranches(String currentAuditor) {

        List<com.fxlabs.issues.dao.entity.branch.Branch> branches = branchRepository.findAll();
        return (Response<List<Branch>>) converter.convertToDtos(branches);
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }
}
