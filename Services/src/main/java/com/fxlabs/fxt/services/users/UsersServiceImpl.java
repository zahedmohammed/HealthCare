package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.converters.users.OrgUsersConverter;
import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.converters.users.UsersPasswordConverter;
import com.fxlabs.fxt.dao.entity.users.*;
import com.fxlabs.fxt.dao.repository.jpa.OrgRepository;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersPasswordRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class UsersServiceImpl extends GenericServiceImpl<Users, com.fxlabs.fxt.dto.users.Users, String> implements UsersService {

    private PasswordEncoder passwordEncoder;
    private OrgRepository orgRepository;
    private OrgUsersRepository orgUsersRepository;
    private UsersPasswordRepository usersPasswordRepository;
    private UsersPasswordConverter usersPasswordConverter;
    private OrgUsersConverter orgUsersConverter;
    //private TextEncryptor encryptor;

    @Autowired
    public UsersServiceImpl(UsersRepository repository, UsersConverter converter, PasswordEncoder passwordEncoder,
                            OrgRepository orgRepository, OrgUsersRepository orgUsersRepository, UsersPasswordRepository usersPasswordRepository,
                            UsersPasswordConverter usersPasswordConverter, /*TextEncryptor encryptor,*/ OrgUsersConverter orgUsersConverter) {
        super(repository, converter);
        this.passwordEncoder = passwordEncoder;
        this.orgRepository = orgRepository;
        this.orgUsersRepository = orgUsersRepository;
        this.usersPasswordRepository = usersPasswordRepository;
        this.usersPasswordConverter = usersPasswordConverter;
        //this.encryptor = encryptor;
        this.orgUsersConverter  = orgUsersConverter;
    }


    public Response<com.fxlabs.fxt.dto.users.Users> findByEmail(String email) {
        Optional<Users> usersOptional = ((UsersRepository) repository).findByEmail(email);

        if (usersOptional.isPresent()) {
            return new Response<com.fxlabs.fxt.dto.users.Users>(converter.convertToDto(usersOptional.get()));
        }
        return new Response<com.fxlabs.fxt.dto.users.Users>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Invalid email or password")));
    }

    public Response<com.fxlabs.fxt.dto.users.UsersPassword> findActivePassword(String email) {
        Response<com.fxlabs.fxt.dto.users.Users> usersResponse = findByEmail(email);
        if (usersResponse.isErrors()) {
            return new Response().withErrors(true).withMessage(usersResponse.getMessages().get(0));
        }
        Optional<UsersPassword> usersPasswordOptional = this.usersPasswordRepository.findByUsersEmailAndActive(email, true);

        if (usersPasswordOptional.isPresent()) {
            return new Response<com.fxlabs.fxt.dto.users.UsersPassword>(usersPasswordConverter.convertToDto(usersPasswordOptional.get()));
        }
        return new Response<com.fxlabs.fxt.dto.users.UsersPassword>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("No active password found!")));
    }

    public Response<Boolean> personalSignUp(com.fxlabs.fxt.dto.users.Users users) {

        String tokens[] = StringUtils.split(users.getEmail(), "@");
        String company = users.getCompany();

        if (StringUtils.isEmpty(users.getCompany())) {
            company = tokens[0];
        }

        return signUp(users, company, OrgType.PERSONAL, Arrays.asList("ROLE_USER"));
    }

    public Response<Boolean> teamSignUp(com.fxlabs.fxt.dto.users.Users users) {
        return signUp(users, users.getCompany(), OrgType.TEAM, Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
    }

    public Response<Boolean> enterpriseSignUp(com.fxlabs.fxt.dto.users.Users users) {
        return signUp(users, users.getCompany(), OrgType.ENTERPRISE, Arrays.asList("ROLE_USER", "ROLE_ADMIN", "ROLE_ENTERPRISE"));
    }

    public Response<Boolean> addToOrg(com.fxlabs.fxt.dto.users.OrgUsers orgUsers, String user) {
        // check user is admin of the org.
        Optional<OrgUsers> orgUsersOptional = this.orgUsersRepository.findByUsersIdAndOrgIdAndStatusAndOrgRole(user, orgUsers.getOrg().getId(), OrgUserStatus.ACTIVE, OrgRole.ADMIN);
        if (!orgUsersOptional.isPresent()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "User not authorized!"));
        }
        // check user, role present
        if (orgUsers.getUsers() == null || orgUsers.getOrgRole() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", "Invalid UserId or Role"));
        }

        OrgUsers ou = new OrgUsers();
        // TODO - Send invite and later when user accepts change it to ACTIVE
        ou.setStatus(OrgUserStatus.ACTIVE);
        Optional<Org> orgOptional = orgRepository.findById(orgUsers.getOrg().getId());
        ou.setOrg(orgOptional.get());
        Optional<Users> usersOptional = repository.findById(orgUsers.getUsers().getId());
        ou.setUsers(usersOptional.get());
        ou.setOrgRole(orgUsersConverter.convertToEntity(orgUsers).getOrgRole());

        this.orgUsersRepository.save(ou);

        return new Response<>(true);
    }

    private Response<Boolean> signUp(com.fxlabs.fxt.dto.users.Users users, String orgName, OrgType orgType, List<String> roles) {

        Response<Boolean> response = new Response<>(false);
        try {
            // check email exists
            if (StringUtils.isEmpty(users.getEmail()) || !EmailValidator.getInstance().isValid(users.getEmail())) {
                return new Response<>(false).withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Invalid email [%s]", users.getEmail())));
            }
            if (!findByEmail(users.getEmail()).isErrors()) {
                return new Response<>(false).withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Already in use email [%s]", users.getEmail())));
            }

            // check username exists
            // check password is not-null and minimum 8 chars.
            if (StringUtils.isEmpty(users.getPassword()) || users.getPassword().length() < 8) {
                return new Response<>(false).withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Password should be minimum [%s] chars", 8)));
            }

            // extract username
            String tokens[] = StringUtils.split(users.getEmail(), "@");

            // check type is either PERSONAL or TEAM
            Org org = new Org();
            org.setCompany(users.getCompany());
            org.setName(orgName);
            org.setBillingEmail(users.getEmail());
            org.setOrgType(orgType);

            org = this.orgRepository.save(org);

            // users
            Users user = new Users();
            user.setEmail(users.getEmail());
            user.setUsername(tokens[0]);
            user.setPrivileges(roles);
            user = repository.save(user);

            // OrgUsers
            OrgUsers orgUsers = new OrgUsers();
            orgUsers.setOrg(org);
            orgUsers.setUsers(user);
            orgUsers.setOrgRole(OrgRole.ADMIN);
            orgUsers.setStatus(OrgUserStatus.ACTIVE);
            orgUsersRepository.save(orgUsers);

            // UsersPassword
            UsersPassword usersPassword = new UsersPassword();
            usersPassword.setUsers(user);
            usersPassword.setGrantKey(users.getPassword());
            usersPassword.setPassword(this.passwordEncoder.encode(users.getPassword()));
            usersPassword.setActive(true);
            usersPasswordRepository.save(usersPassword);


            return new Response<>(true).withMessage(new Message(MessageType.INFO, "", "Sign-up successful!"));
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            return new Response<>(false).withErrors(true).withMessage(new Message(MessageType.ERROR, "", ex.getLocalizedMessage()));
        }
    }

    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }

}
