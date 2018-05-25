package com.fxlabs.fxt.services.vault;

import com.fxlabs.fxt.converters.vault.VaultConverter;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.entity.vault.Vault;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersRepository;
import com.fxlabs.fxt.dao.repository.jpa.VaultRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Response<List<com.fxlabs.fxt.dto.vault.Vault>> findAll(String org, Pageable pageable) {
        Page<Vault> page = this.repository.findByOrgId(org, pageable);
        return new Response<>(converter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<com.fxlabs.fxt.dto.vault.Vault> create(com.fxlabs.fxt.dto.vault.Vault dto, String o, String user) {
        NameDto org_ = new NameDto();
        org_.setId(o);
        dto.setOrg(org_);

        // empty key
        if (org.springframework.util.StringUtils.isEmpty(dto.getKey())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid key"));
        }

        // empty value
        if (org.springframework.util.StringUtils.isEmpty(dto.getVal())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid val"));
        }
        // duplicate key
        Optional<Vault> optionalVault = repository.findByKeyAndOrgId(dto.getKey(), o);
        if (optionalVault.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Key with the name exists."));
        }

        return super.save(dto, user);
    }

    @Override
    public Response<com.fxlabs.fxt.dto.vault.Vault> update(com.fxlabs.fxt.dto.vault.Vault dto, String org, String user) {
        if (!StringUtils.equals(dto.getOrg().getId(), org)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
        return super.save(dto, user);
    }

    @Override
    public Response<com.fxlabs.fxt.dto.vault.Vault> delete(String id, String org, String user) {
        // duplicate key
        Optional<Vault> optionalVault = repository.findByIdAndOrgId(id, org);
        if (!optionalVault.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Key with the name doesn't exists."));
        }
        return delete(id, user);
    }

    @Override
    public Response<com.fxlabs.fxt.dto.vault.Vault> save(com.fxlabs.fxt.dto.vault.Vault dto, String user) {

        // empty key
        if (org.springframework.util.StringUtils.isEmpty(dto.getKey())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid key"));
        }

        // empty value
        if (org.springframework.util.StringUtils.isEmpty(dto.getVal())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid val"));
        }

        return super.save(dto, user);
    }

    @Override
    public Response<String> findByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return new Response<>(StringUtils.EMPTY);
        }
        String[] tokens = name.split("/");
        String org = tokens[0];
        String key = tokens[1];

        Optional<Vault> vaultOptional = this.repository.findByOrgNameAndKey(org, key);

        if (!vaultOptional.isPresent()) {
            return new Response<>(StringUtils.EMPTY);
        }

        return new Response<>(vaultOptional.get().getVal());

    }

    @Override
    public void isUserEntitled(String id, String user) {
        /*Optional<Vault> vaultOptional = repository.findById(id);

        if (!vaultOptional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", id));
        }

        if (!org.apache.commons.lang3.StringUtils.equals(vaultOptional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }*/

    }

}
