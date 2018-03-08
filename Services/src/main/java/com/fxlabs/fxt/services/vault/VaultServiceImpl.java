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
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<Vault> vaultOptional = repository.findById(id);

        if (!vaultOptional.isPresent()) {
            throw new FxException(String.format("Resource [%s] not found.", id));
        }

        if (!org.apache.commons.lang3.StringUtils.equals(vaultOptional.get().getCreatedBy(), user)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }


    }

}
