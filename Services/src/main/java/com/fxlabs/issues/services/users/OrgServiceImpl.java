package com.fxlabs.issues.services.users;

import com.fxlabs.issues.converters.users.OrgConverter;
import com.fxlabs.issues.converters.users.OrgUsersConverter;
import com.fxlabs.issues.dao.entity.users.*;
import com.fxlabs.issues.dao.repository.es.OrgUsersESRepository;
import com.fxlabs.issues.dao.repository.jpa.OrgRepository;
import com.fxlabs.issues.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.issues.dao.repository.jpa.UsersRepository;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.base.UserMinimalDto;
import com.fxlabs.issues.dto.users.Member;
import com.fxlabs.issues.dto.users.OrgType;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.fxlabs.issues.services.exceptions.FxException;
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
public class OrgServiceImpl extends GenericServiceImpl<Org, com.fxlabs.issues.dto.users.Org, String> implements OrgService {

    private OrgRepository orgRepository;
    private OrgConverter orgConverter;

    private OrgUsersRepository orgUsersRepository;
    private OrgUsersESRepository orgUsersESRepository;
    private OrgUsersConverter orgUsersConverter;

    private UsersRepository usersRepository;
    private UsersService usersService;

    public static final Collection<OrgRole> roles = Arrays.asList(OrgRole.ADMIN, OrgRole.ENTERPRISE_ADMIN);

    @Autowired
    public OrgServiceImpl(OrgUsersRepository orgUsersRepository, OrgUsersESRepository orgUsersESRepository,
                          OrgUsersConverter orgUsersConverter, OrgRepository orgRepository, OrgConverter orgConverter,
                          UsersRepository usersRepository, UsersService usersService) {

        super(orgRepository, orgConverter);

        this.orgRepository = orgRepository;
        this.orgConverter = orgConverter;

        this.orgUsersRepository = orgUsersRepository;
        this.orgUsersESRepository = orgUsersESRepository;
        this.orgUsersConverter = orgUsersConverter;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    @Override
    public Response<com.fxlabs.issues.dto.users.Org> findById(String id) {
        Optional<Org> optionalOrg = this.orgRepository.findById(id);
        com.fxlabs.issues.dto.users.Org org = null;
        if (optionalOrg.isPresent()) {
            org = orgConverter.convertToDto(optionalOrg.get());
        }
        return new Response<com.fxlabs.issues.dto.users.Org>(org);
    }

    @Override
    public Response<List<com.fxlabs.issues.dto.users.OrgUsers>> findByAccess(String user, Pageable pageable) {
        Page<OrgUsers> page = this.orgUsersRepository.findByUsersIdAndStatusAndOrgRoleIn(user, OrgUserStatus.ACTIVE, roles, pageable);
        return new Response<>(orgUsersConverter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<List<com.fxlabs.issues.dto.users.Org>> findAll(String user, Pageable pageable) {

        Page<OrgUsers> orgUsers = orgUsersRepository.findByUsersIdAndStatusAndOrgRoleIn(user, OrgUserStatus.ACTIVE, roles, pageable);

        List<Org> orgs = new ArrayList<>();
        orgUsers.forEach(ou -> {
            orgs.add(ou.getOrg());
        });
        return new Response<>(converter.convertToDtos(orgs), orgUsers.getTotalElements(), orgs.size());
    }

    @Override
    public Response<com.fxlabs.issues.dto.users.Org> save(com.fxlabs.issues.dto.users.Org dto, String user) {
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
        dto.setOrgPlan(com.fxlabs.issues.dto.users.OrgPlan.ENTERPRISE);

        Response<com.fxlabs.issues.dto.users.Org> response = super.save(dto, user);

        // OrgUsers
        OrgUsers orgUsers = new OrgUsers();
        Org org = orgConverter.convertToEntity(response.getData());
        orgUsers.setOrg(org);
        orgUsers.setUsers(users);
        orgUsers.setOrgRole(OrgRole.ADMIN);
        orgUsers.setStatus(OrgUserStatus.ACTIVE);
        orgUsersRepository.save(orgUsers);


        return response;
    }

    @Override
    public Response<com.fxlabs.issues.dto.users.Org> update(com.fxlabs.issues.dto.users.Org dto, String user) {
        // TODO - check dup name
        if (StringUtils.isEmpty(dto.getCompany())) {
            throw new FxException(String.format("Invalid company [%s] value.", dto.getCompany()));
        }

        if (StringUtils.isEmpty(dto.getBillingEmail())) {
            throw new FxException(String.format("Invalid Contact-email [%s] value.", dto.getBillingEmail()));
        }

        Response<com.fxlabs.issues.dto.users.Org> response = super.save(dto, user);

        return response;
    }

    @Override
    public Response<List<com.fxlabs.issues.dto.users.OrgUsers>> findOrgUsers(String org, String user, Pageable pageable) {
        Page<OrgUsers> page = this.orgUsersRepository.findByOrgId(org, pageable);
        return new Response<>(orgUsersConverter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public Response<Boolean> addMember(Member dto, String orgId, String user) {

        // role check
        if (dto.getOrgRole() == null) {
            dto.setOrgRole(com.fxlabs.issues.dto.users.OrgRole.USER);
        }

        com.fxlabs.issues.dto.users.Users users = new com.fxlabs.issues.dto.users.Users();
        users.setName(dto.getName());
        users.setEmail(dto.getEmail());

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return new Response<>(false).withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Password and Confirm Password did not match.")));
        }
        users.setPassword(dto.getPassword());

        Response<com.fxlabs.issues.dto.users.Users> addUserResponse = usersService.addUser(users, Arrays.asList("ROLE_USER"));

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
    public Response<Boolean> saveUser(String id, UserMinimalDto users, com.fxlabs.issues.dto.users.OrgUsers orgUser, String orgId, String user) {

        // check user-id belongs to org
        Optional<OrgUsers> usersOptional = this.orgUsersRepository.findByOrgIdAndUsersId(orgId, id);
        if (!usersOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", id, orgId));
        }

        if (!StringUtils.equals(id, users.getId())) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", id, orgId));
        }

        // save user's name, company etc
        Response<com.fxlabs.issues.dto.users.Users> usersResponse = this.usersService.findById(id);
        usersResponse.getData().setName(users.getName());
        usersResponse.getData().setCompany(users.getCompany());
        // usersResponse.getData().setInactive(users.isInactive());


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
        orgUsers_.setStatus(OrgUserStatus.valueOf(orgUser.getStatus().toString()));
        this.orgUsersRepository.save(orgUsers_);

        return new Response<>(true);


    }

    @Override
    public Response<com.fxlabs.issues.dto.users.OrgUsers> getUserByOrgUserId(String orgUserId, String orgId) {

        Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findById(orgUserId);


        if (!orgUsersOptional.isPresent() || !StringUtils.equals(orgUsersOptional.get().getOrg().getId(), orgId)) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", orgUserId, orgId));
        }

        return new Response<>(orgUsersConverter.convertToDto(orgUsersOptional.get()));

    }

    @Override
    public Response<com.fxlabs.issues.dto.users.Org> findByName(String orgName) {

        Optional<Org> orgOptional = this.orgRepository.findByName(orgName);

        if (!orgOptional.isPresent()) {
            throw new FxException("User not entitled to the resource");
        }
        return new Response<>(converter.convertToDto(orgOptional.get()));

    }

    @Override
    public Response<com.fxlabs.issues.dto.users.OrgUsers> getUser(String id, String orgId) {

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

    @Override
    public Response<List<com.fxlabs.issues.dto.users.Org>> searchOrg(String keyword, String user, Pageable pageable) {
        if (StringUtils.isNotEmpty(keyword)) {
            Set<OrgUsers> orgUsers = orgUsersRepository.findByUsersIdAndStatusAndOrgRoleInAndOrgNameContainingIgnoreCase(user, OrgUserStatus.ACTIVE, roles, keyword);

            List<Org> orgs = new ArrayList<>();
            orgUsers.forEach(ou -> {
                orgs.add(ou.getOrg());
            });
            List<com.fxlabs.issues.dto.users.Org> orgs_ = converter.convertToDtos(orgs);

            return new Response<List<com.fxlabs.issues.dto.users.Org>>(orgs_);

        } else {

            return findAll(user, pageable);
        }


    }
}
