package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.converters.users.OrgConverter;
import com.fxlabs.fxt.converters.users.OrgUsersConverter;
import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.clusters.AccountType;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import com.fxlabs.fxt.dao.entity.users.*;
import com.fxlabs.fxt.dao.repository.es.OrgUsersESRepository;
import com.fxlabs.fxt.dao.repository.jpa.AccountRepository;
import com.fxlabs.fxt.dao.repository.jpa.OrgRepository;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.base.UserMinimalDto;
import com.fxlabs.fxt.dto.users.Member;
import com.fxlabs.fxt.dto.users.OrgType;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class OrgServiceImpl extends GenericServiceImpl<Org, com.fxlabs.fxt.dto.users.Org, String> implements OrgService {

    private OrgRepository orgRepository;
    private OrgConverter orgConverter;

    private OrgUsersRepository orgUsersRepository;
    private OrgUsersESRepository orgUsersESRepository;
    private OrgUsersConverter orgUsersConverter;

    private UsersRepository usersRepository;
    private UsersService usersService;
    private AccountRepository accountRepository;

    @Autowired
    public OrgServiceImpl(OrgUsersRepository orgUsersRepository, OrgUsersESRepository orgUsersESRepository,
                          OrgUsersConverter orgUsersConverter, OrgRepository orgRepository, OrgConverter orgConverter,
                          UsersRepository usersRepository, UsersService usersService, AccountRepository accountRepository) {

        super(orgRepository, orgConverter);

        this.orgRepository = orgRepository;
        this.orgConverter = orgConverter;

        this.orgUsersRepository = orgUsersRepository;
        this.orgUsersESRepository = orgUsersESRepository;
        this.orgUsersConverter = orgUsersConverter;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
        this.accountRepository = accountRepository;

    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.users.OrgUsers>> findByAccess(String user, Pageable pageable) {
        Page<OrgUsers> page = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN, pageable);
        return new Response<>(orgUsersConverter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.users.Org>> findAll(String user, Pageable pageable) {
        Set<OrgUsers> orgUsers = orgUsersRepository.findByUsersIdAndStatusAndOrgRole(user, OrgUserStatus.ACTIVE, OrgRole.ADMIN);
        List<Org> orgs = new ArrayList<>();
        orgUsers.forEach(ou -> {
            orgs.add(ou.getOrg());
        });
        return new Response<>(converter.convertToDtos(orgs), new Long(orgs.size()), orgs.size());
    }

    @Override
    public Response<com.fxlabs.fxt.dto.users.Org> save(com.fxlabs.fxt.dto.users.Org dto, String user) {
        // TODO - check dup name
        Optional<Org> orgOptional = orgRepository.findByName(dto.getName());
        if (orgOptional.isPresent()) {
            throw new FxException(String.format("Org name [%s] taken.", dto.getName()));
        }

        Optional<Users> usersOptional = usersRepository.findById(user);
        if (!usersOptional.isPresent()) {
            throw new FxException(String.format("Invalid user [%s].", user));
        }
        Users users = usersOptional.get();

        dto.setOrgType(OrgType.ENTERPRISE);

        Response<com.fxlabs.fxt.dto.users.Org> response = super.save(dto, user);

        // OrgUsers
        OrgUsers orgUsers = new OrgUsers();
        orgUsers.setOrg(orgConverter.convertToEntity(response.getData()));
        orgUsers.setUsers(users);
        orgUsers.setOrgRole(OrgRole.ADMIN);
        orgUsers.setStatus(OrgUserStatus.ACTIVE);
        orgUsersRepository.save(orgUsers);

        Account ca = new Account();

        ca.setOrg(orgConverter.convertToEntity(response.getData()));
        ca.setAccountType(AccountType.Self_Hosted);
        ca.setCreatedBy(users.getId());
        ca.setInactive(false);
        ca.setName("Default");
        ca.setVisibility(com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility.ORG_PUBLIC);

        this.accountRepository.save(ca);

        Account caGithub = new Account();

        caGithub.setOrg(orgConverter.convertToEntity(response.getData()));
        caGithub.setAccountType(AccountType.GitHub);
        caGithub.setCreatedBy(users.getId());
        caGithub.setInactive(false);
        caGithub.setName("Default");
        caGithub.setVisibility(ClusterVisibility.ORG_PUBLIC);

        this.accountRepository.save(caGithub);

        Account caEmail = new Account();

        caEmail.setOrg(orgConverter.convertToEntity(response.getData()));
        caEmail.setAccountType(AccountType.Email);
        caEmail.setCreatedBy(users.getId());
        caEmail.setInactive(false);
        caEmail.setName("Default");
        caEmail.setVisibility(com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility.ORG_PUBLIC);

        this.accountRepository.save(caEmail);


        return response;
    }

    @Override
    public Response<com.fxlabs.fxt.dto.users.Org> update(com.fxlabs.fxt.dto.users.Org dto, String user) {
        // TODO - check dup name
        if (StringUtils.isEmpty(dto.getCompany())) {
            throw new FxException(String.format("Invalid company [%s] value.", dto.getCompany()));
        }

        if (StringUtils.isEmpty(dto.getBillingEmail())) {
            throw new FxException(String.format("Invalid Contact-email [%s] value.", dto.getBillingEmail()));
        }

        Response<com.fxlabs.fxt.dto.users.Org> response = super.save(dto, user);

        return response;
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.users.OrgUsers>> findOrgUsers(String org, String user, Pageable pageable) {
        // Check user is has admin access to org.
        Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndOrgRole(org, user, OrgRole.ADMIN);
        if (!orgUsersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, org));
        }

        Page<OrgUsers> page = this.orgUsersRepository.findByOrgId(org, pageable);
        return new Response<>(orgUsersConverter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<Boolean> addMember(Member dto, String orgId, String user) {

        // Check user is has admin access to org.
        Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndOrgRole(dto.getOrgId(), user, OrgRole.ADMIN);
        if (!orgUsersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, dto.getOrgId()));
        }

        // role check
        if (dto.getOrgRole() == null) {
            dto.setOrgRole(com.fxlabs.fxt.dto.users.OrgRole.USER);
        }

        com.fxlabs.fxt.dto.users.Users users = new com.fxlabs.fxt.dto.users.Users();
        users.setName(dto.getName());
        users.setEmail(dto.getEmail());
        users.setPassword(dto.getPassword());

        Response<com.fxlabs.fxt.dto.users.Users> addUserResponse = usersService.addUser(users, Arrays.asList("ROLE_USER"));

        if (addUserResponse.isErrors() || addUserResponse.getData() == null) {
            return new Response<>(false).withErrors(true).withMessages(addUserResponse.getMessages());
        }

        Users u = new Users();
        u.setId(addUserResponse.getData().getId());

        OrgUsers orgUsers = new OrgUsers();
        orgUsers.setOrg(orgUsersOptional.get().getOrg());
        orgUsers.setUsers(u);
        orgUsers.setStatus(OrgUserStatus.ACTIVE);

        orgUsers.setOrgRole(OrgRole.valueOf(dto.getOrgRole().name()));

        orgUsers = orgUsersRepository.save(orgUsers);

        return new Response<>(true);
    }

    @Override
    public Response<Boolean> resetPassword(String id, Member member, String orgId, String user) {

        // Check user is has admin access to org.
        Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndOrgRole(orgId, user, OrgRole.ADMIN);
        if (!orgUsersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, orgId));
        }

        // check user-id belongs to org
        Optional<OrgUsers> usersOptional = this.orgUsersRepository.findByOrgIdAndUsersId(orgId, id);
        if (!usersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", id, orgId));
        }

        return this.usersService.resetPassword(id, member.getPassword(), member.getConfirmPassword());
    }

    @Override
    public Response<Boolean> saveUser(String id, UserMinimalDto users, com.fxlabs.fxt.dto.users.OrgUsers orgUser, String orgId, String user) {

        // Check user is has admin access to org.
        Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndOrgRole(orgId, user, OrgRole.ADMIN);
        if (!orgUsersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, orgId));
        }

        // check user-id belongs to org
        Optional<OrgUsers> usersOptional = this.orgUsersRepository.findByOrgIdAndUsersId(orgId, id);
        if (!usersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", id, orgId));
        }

        if (!StringUtils.equals(id, users.getId())) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", id, orgId));
        }

        // save user's name, company etc
        Response<com.fxlabs.fxt.dto.users.Users> usersResponse = this.usersService.findById(id);
        usersResponse.getData().setName(users.getName());
        usersResponse.getData().setCompany(users.getCompany());

        this.usersService.save(usersResponse.getData());


        // save OrgUser
        this.orgUsersRepository.save(orgUsersConverter.convertToEntity(orgUser));

        return new Response<>(true);


    }

    @Override
    public void isUserEntitled(String s, String user) {
        Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndStatus(s, user, OrgUserStatus.ACTIVE);
        if (orgUsersOptional.isPresent() && orgUsersOptional.get().getOrgRole() == OrgRole.ADMIN) {
            return;
        }
        throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, s));
    }


}
