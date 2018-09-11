package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.converters.users.OrgConverter;
import com.fxlabs.fxt.converters.users.OrgUsersConverter;
import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.clusters.AccountType;
import com.fxlabs.fxt.dao.entity.it.IssueTracker;
import com.fxlabs.fxt.dao.entity.notify.Notification;
import com.fxlabs.fxt.dao.entity.users.*;
import com.fxlabs.fxt.dao.repository.es.OrgUsersESRepository;
import com.fxlabs.fxt.dao.repository.jpa.*;
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
    private IssueTrackerRepository issueTrackerRepository;
    private NotificationRepository notificationRepository;

    public static final Collection<OrgRole> roles = Arrays.asList(OrgRole.ADMIN, OrgRole.ENTERPRISE_ADMIN);

    @Autowired
    public OrgServiceImpl(OrgUsersRepository orgUsersRepository, OrgUsersESRepository orgUsersESRepository,
                          OrgUsersConverter orgUsersConverter, OrgRepository orgRepository, OrgConverter orgConverter, NotificationRepository notificationRepository,
                          UsersRepository usersRepository, IssueTrackerRepository issueTrackerRepository, UsersService usersService, AccountRepository accountRepository) {

        super(orgRepository, orgConverter);

        this.orgRepository = orgRepository;
        this.orgConverter = orgConverter;

        this.orgUsersRepository = orgUsersRepository;
        this.orgUsersESRepository = orgUsersESRepository;
        this.orgUsersConverter = orgUsersConverter;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
        this.accountRepository = accountRepository;
        this.issueTrackerRepository = issueTrackerRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.users.OrgUsers>> findByAccess(String user, Pageable pageable) {
        Page<OrgUsers> page = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRoleIn(user, OrgUserStatus.ACTIVE, roles, pageable);
        return new Response<>(orgUsersConverter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.users.Org>> findAll(String user, Pageable pageable) {

        Page<OrgUsers> orgUsers = orgUsersRepository.findByUsersIdAndStatusAndOrgRoleIn(user, OrgUserStatus.ACTIVE, roles, pageable);

        List<Org> orgs = new ArrayList<>();
        orgUsers.forEach(ou -> {
            orgs.add(ou.getOrg());
        });
        return new Response<>(converter.convertToDtos(orgs), new Long(orgs.size()), orgs.size());
    }

    @Override
    public Response<com.fxlabs.fxt.dto.users.Org> save(com.fxlabs.fxt.dto.users.Org dto, String user) {
        // check dup name
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
        dto.setOrgPlan(com.fxlabs.fxt.dto.users.OrgPlan.ENTERPRISE);

        Response<com.fxlabs.fxt.dto.users.Org> response = super.save(dto, user);

        // OrgUsers
        OrgUsers orgUsers = new OrgUsers();
        Org org = orgConverter.convertToEntity(response.getData());
        orgUsers.setOrg(org);
        orgUsers.setUsers(users);
        orgUsers.setOrgRole(OrgRole.ADMIN);
        orgUsers.setStatus(OrgUserStatus.ACTIVE);
        orgUsersRepository.save(orgUsers);

        Account ca = new Account();

        ca.setOrg(org);
        ca.setAccountType(AccountType.Self_Hosted);
        ca.setCreatedBy(users.getId());
        ca.setInactive(false);
        ca.setName("Default_SelfHosted");

        this.accountRepository.save(ca);

        /*Account caGithub = new Account();
        caGithub.setOrg(org);
        caGithub.setAccountType(AccountType.GitHub);
        caGithub.setCreatedBy(users.getId());
        caGithub.setInactive(false);
        caGithub.setName("Default_GitHub");
        Account gitSavedEntity = this.accountRepository.save(caGithub);

        IssueTracker issueTrackerGitHub = new IssueTracker();
        issueTrackerGitHub.setOrg(org);
        issueTrackerGitHub.setAccount(gitSavedEntity);
        issueTrackerGitHub.setName("Dev-IssueTracker");
        issueTrackerRepository.save(issueTrackerGitHub);

        Account caSlack = new Account();
        caSlack.setOrg(org);
        caSlack.setAccountType(AccountType.Slack);
        caSlack.setCreatedBy(users.getId());
        caSlack.setInactive(false);
        caSlack.setName("Default_Slack");
        Account accountSlackEntity = this.accountRepository.save(caSlack);

        Notification notificationSlack =  new Notification();
        notificationSlack.setOrg(org);
        notificationSlack.setName("Dev-Slack-Notification");
        notificationSlack.setAccount(accountSlackEntity);
        notificationRepository.save(notificationSlack);

        Account caEmail = new Account();
        caEmail.setOrg(org);
        caEmail.setAccountType(AccountType.Email);
        caEmail.setCreatedBy(users.getId());
        caEmail.setInactive(false);
        caEmail.setName("Default_Email");
        Account caEmailEntity = this.accountRepository.save(caEmail);

        Notification notificationEmail =  new Notification();
        notificationEmail.setOrg(org);
        notificationEmail.setName("Dev-Email-Notification");
        notificationEmail.setAccount(caEmailEntity);
        notificationRepository.save(notificationEmail);*/


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
        Page<OrgUsers> page = this.orgUsersRepository.findByOrgId(org, pageable);
        return new Response<>(orgUsersConverter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<Boolean> addMember(Member dto, String orgId, String user) {

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
        Org o = new Org();
        o.setId(orgId);
        orgUsers.setOrg(o);
        orgUsers.setUsers(u);
        orgUsers.setStatus(OrgUserStatus.ACTIVE);

        orgUsers.setOrgRole(OrgRole.valueOf(dto.getOrgRole().name()));

        orgUsers = orgUsersRepository.save(orgUsers);

        return new Response<>(true);
    }

    @Override
    public Response<Boolean> resetPassword(String id, Member member, String orgId, String auditoryUserId, String auditorOrgId) {

        // check user-id belongs to org
        Optional<OrgUsers> usersOptional = this.orgUsersRepository.findByOrgIdAndUsersId(orgId, id);
        if (!usersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", id, orgId));
        }

       // Optional<OrgUsers> usersOptional_ = this.orgUsersRepository.findByOrgIdAndUsersId(auditorOrgId, id);
        if (!orgId.equals(auditorOrgId)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", id, orgId));
        }

        Optional<OrgUsers> auditorUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersId(auditorOrgId, auditoryUserId);
        if (!auditorUsersOptional.isPresent() || !auditorUsersOptional.get().getOrgRole().equals(OrgRole.ADMIN)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", id, orgId));
        }


        return this.usersService.resetPassword(id, member.getPassword(), member.getConfirmPassword());
    }

    @Override
    public Response<Boolean> saveUser(String id, UserMinimalDto users, com.fxlabs.fxt.dto.users.OrgUsers orgUser, String orgId, String user) {

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
        // Shouldn't be able to demote/promote an EA.

        OrgUsers orgUsers_ = usersOptional.get();
        OrgUsers orgUsers = orgUsersConverter.convertToEntity(orgUser);

        if (orgUsers_.getOrgRole() == OrgRole.ENTERPRISE_ADMIN && orgUsers.getOrgRole() != OrgRole.ENTERPRISE_ADMIN) {
            throw new FxException(String.format("User role [%s] can't be changed.", OrgRole.ENTERPRISE_ADMIN));
        } else if (orgUsers_.getOrgRole() != OrgRole.ENTERPRISE_ADMIN && orgUsers.getOrgRole() == OrgRole.ENTERPRISE_ADMIN) {
            throw new FxException(String.format("User role can't be changed to [%s].", OrgRole.ENTERPRISE_ADMIN));
        }
        orgUsers_.setOrgRole(OrgRole.valueOf(orgUser.getOrgRole().toString()));
        this.orgUsersRepository.save(orgUsers_);

        return new Response<>(true);


    }

    @Override
    public Response<com.fxlabs.fxt.dto.users.OrgUsers> getUserByOrgUserId(String orgUserId, String orgId) {

        Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findById(orgUserId);


        if (!orgUsersOptional.isPresent() || !StringUtils.equals(orgUsersOptional.get().getOrg().getId(), orgId)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", orgUserId, orgId));
        }

        return new Response<>(orgUsersConverter.convertToDto(orgUsersOptional.get()));

    }

    @Override
    public Response<com.fxlabs.fxt.dto.users.Org> findByName(String orgName) {

        Optional<Org> orgOptional = this.orgRepository.findByName(orgName);
        if (!orgOptional.isPresent()) {
            throw new FxException("User not entitled to the resource");
        }
        return new Response<>(converter.convertToDto(orgOptional.get()));

    }

    @Override
    public Response<com.fxlabs.fxt.dto.users.OrgUsers> getUser(String id, String orgId) {

        // check user-id belongs to org
        Optional<OrgUsers> usersOptional = this.orgUsersRepository.findByOrgIdAndUsersId(orgId, id);
        if (!usersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", id, orgId));
        }

        return new Response<>(orgUsersConverter.convertToDto(usersOptional.get()));

    }

    @Override
    public void isUserEntitled(String s, String user) {
        /*Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findByOrgIdAndUsersIdAndStatus(s, user, OrgUserStatus.ACTIVE);
        if (orgUsersOptional.isPresent() && orgUsersOptional.get().getOrgRole() == OrgRole.ADMIN) {
            return;
        }
        throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, s));*/
    }


}
