package com.fxlabs.fxt.services.vault;

import com.fxlabs.fxt.converters.run.RunConverter;
import com.fxlabs.fxt.converters.run.SuiteConverter;
import com.fxlabs.fxt.converters.run.TestSuiteResponseConverter;
import com.fxlabs.fxt.converters.vault.VaultConverter;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.users.*;
import com.fxlabs.fxt.dao.entity.vault.Vault;
import com.fxlabs.fxt.dao.repository.es.SuiteESRepository;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.dto.run.*;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.project.ProjectService;
import com.fxlabs.fxt.services.run.RunService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class VaultServiceImpl extends GenericServiceImpl<Vault, com.fxlabs.fxt.dto.vault.Vault, String> implements VaultService {

    private VaultRepository repository;
    private VaultConverter converter;
    private UsersRepository usersRepository;
    private OrgUsersRepository orgUsersRepository;

    @Autowired
    public VaultServiceImpl(VaultRepository repository, VaultConverter converter, UsersRepository usersRepository, OrgUsersRepository orgUsersRepository) {
        super(repository, converter);
        this.repository = repository;
        this.converter = converter;
        this.usersRepository = usersRepository;
        this.orgUsersRepository = orgUsersRepository;
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.vault.Vault>> findAll(String user, Pageable pageable) {
        List<Vault> vaults = this.repository.findByCreatedBy(user, pageable);
        return new Response<>(converter.convertToDtos(vaults));
    }

    @Override
    public Response<com.fxlabs.fxt.dto.vault.Vault> save(com.fxlabs.fxt.dto.vault.Vault dto, String user) {

        if (dto.getOrg() == null) {
            Set<OrgUsers> set = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
            if (CollectionUtils.isEmpty(set)) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("You don't have [ADMIN] access to any Org. Set org with [WRITE] access.")));
            }

            OrgUsers orgUsers = null;
            orgUsers = set.iterator().next();
            NameDto org = new NameDto();
            org.setId(orgUsers.getOrg().getId());
            dto.setOrg(org);

        }
        return super.save(dto, user);
    }

    @Override
    public void isUserEntitled(String id, String user) {
        Optional<Vault> vaultOptional = repository.findById(id);

        if (!vaultOptional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", id));
        }

        if (!org.apache.commons.lang3.StringUtils.equals(vaultOptional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }


    }

}
