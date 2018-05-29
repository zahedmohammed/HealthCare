package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.converters.users.OrgUsersConverter;
import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.converters.users.UsersPasswordConverter;
import com.fxlabs.fxt.dao.entity.users.*;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Service
@Transactional
public class UsersServiceImpl extends GenericServiceImpl<Users, com.fxlabs.fxt.dto.users.Users, String> implements UsersService {

    private PasswordEncoder passwordEncoder;
    private OrgRepository orgRepository;
    private OrgUsersRepository orgUsersRepository;
    private AccessKeyRepository accessKeyRepository;
    private UsersPasswordRepository usersPasswordRepository;
    private UsersPasswordConverter usersPasswordConverter;
    private OrgUsersConverter orgUsersConverter;
    //private TextEncryptor encryptor;

    @Autowired
    public UsersServiceImpl(UsersRepository repository, UsersConverter converter, PasswordEncoder passwordEncoder, AccessKeyRepository accessKeyRepository,
                            OrgRepository orgRepository, OrgUsersRepository orgUsersRepository, UsersPasswordRepository usersPasswordRepository,
                            UsersPasswordConverter usersPasswordConverter, /*TextEncryptor encryptor,*/ OrgUsersConverter orgUsersConverter) {
        super(repository, converter);
        this.passwordEncoder = passwordEncoder;
        this.orgRepository = orgRepository;
        this.orgUsersRepository = orgUsersRepository;
        this.usersPasswordRepository = usersPasswordRepository;
        this.usersPasswordConverter = usersPasswordConverter;
        //this.encryptor = encryptor;
        this.orgUsersConverter = orgUsersConverter;
        this.accessKeyRepository = accessKeyRepository;
    }


    @Override
    public Response<com.fxlabs.fxt.dto.users.Users> findByEmail(String email) {
        Optional<Users> usersOptional = ((UsersRepository) repository).findByEmail(email);

        if (usersOptional.isPresent()) {
            return new Response<com.fxlabs.fxt.dto.users.Users>(converter.convertToDto(usersOptional.get()));
        }
        return new Response<com.fxlabs.fxt.dto.users.Users>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Invalid email or password")));
    }

    @Override
    public Response<com.fxlabs.fxt.dto.users.Users> findById(String id) {
        Optional<Users> usersOptional = ((UsersRepository) repository).findById(id);

        if (usersOptional.isPresent()) {
            return new Response<com.fxlabs.fxt.dto.users.Users>(converter.convertToDto(usersOptional.get()));
        }
        return new Response<com.fxlabs.fxt.dto.users.Users>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Invalid id")));
    }

    public Response<String> generate(String email){
        Optional<Users> usersOptional = ((UsersRepository) repository).findByEmail(email);

        if (!usersOptional.isPresent()) {
            return new Response<String>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Invalid id")));
        }

        String secretKey = RandomStringUtils.randomAlphabetic(16);
        String accessKey = RandomStringUtils.randomAlphabetic(16);
        // AccessKey
        AccessKey ak = new AccessKey();
        ak.setAccessKey(accessKey);
        ak.setSecretKey(this.passwordEncoder.encode(secretKey));
        ak.setUsers(usersOptional.get());
        ak.setExpiration(DateUtils.addMinutes(new Date(), 10));
        ak = accessKeyRepository.save(ak);


        String response = accessKey + ":" + secretKey;
        return new Response<String>(response);
    }

    @Override
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

    @Override
    public Response<Boolean> personalSignUp(com.fxlabs.fxt.dto.users.Users users) {

        String tokens[] = StringUtils.split(users.getEmail(), "@");
        String company = users.getCompany();

        if (StringUtils.isEmpty(users.getCompany())) {
            company = users.getEmail();
        }

        return signUp(users, company, OrgType.PERSONAL, Arrays.asList("ROLE_USER"));
    }

    @Override
    public Response<Boolean> teamSignUp(com.fxlabs.fxt.dto.users.Users users) {
        return signUp(users, users.getCompany(), OrgType.TEAM, Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
    }

    @Override
    public Response<Boolean> enterpriseSignUp(com.fxlabs.fxt.dto.users.Users users) {
        return signUp(users, users.getCompany(), OrgType.ENTERPRISE, Arrays.asList("ROLE_USER", "ROLE_ADMIN", "ROLE_ENTERPRISE"));
    }

    @Override
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


            Response<com.fxlabs.fxt.dto.users.Users> addUserResponse = addUser(users, roles);
            if (addUserResponse.isErrors() || addUserResponse.getData() == null) {
                return new Response<>(false).withErrors(true).withMessages(addUserResponse.getMessages());
            }

            Users user = converter.convertToEntity(addUserResponse.getData());

            if (StringUtils.isEmpty(orgName) || orgName.length() < 3) {
                return new Response<>(false).withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Company should be minimum [%s] chars", 3)));
            }

            // check type is either PERSONAL or TEAM
            Optional<Org> orgOptional = this.orgRepository.findByName(users.getCompany());
            if (orgOptional.isPresent()) {
                return new Response<>(false).withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Org name [%s] exists.", orgName)));
            }

            Org org = new Org();
            org.setCompany(orgName);
            org.setName(orgName);
            org.setBillingEmail(users.getEmail());
            org.setOrgType(orgType);

            // OrgUsers
            OrgUsers orgUsers = new OrgUsers();
            orgUsers.setOrg(org);
            orgUsers.setUsers(user);
            orgUsers.setOrgRole(OrgRole.ADMIN);
            orgUsers.setStatus(OrgUserStatus.ACTIVE);
            orgUsersRepository.save(orgUsers);

            org = this.orgRepository.save(org);

            return new Response<>(true).withMessage(new Message(MessageType.INFO, "", "Sign-up successful!"));
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            return new Response<>(false).withErrors(true).withMessage(new Message(MessageType.ERROR, "", ex.getLocalizedMessage()));
        }
    }

    @Override
    public Response<com.fxlabs.fxt.dto.users.Users> addUser(com.fxlabs.fxt.dto.users.Users users, List<String> roles) {
        // check email exists
        if (StringUtils.isEmpty(users.getEmail()) || !EmailValidator.getInstance().isValid(users.getEmail())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Invalid email [%s]", users.getEmail())));
        }
        if (!findByEmail(users.getEmail()).isErrors()) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Already in use email [%s]", users.getEmail())));
        }

        // check username exists
        // check password is not-null and minimum 8 chars.
        if (StringUtils.isEmpty(users.getPassword()) || users.getPassword().length() < 8) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Password should be minimum [%s] chars", 8)));
        }

        // extract username
        String tokens[] = StringUtils.split(users.getEmail(), "@");

        // users
        Users user = new Users();
        user.setName(users.getName());
        user.setEmail(users.getEmail());
        user.setUsername(tokens[0]);
        user.setPrivileges(roles);
        user = repository.save(user);

        // UsersPassword
        UsersPassword usersPassword = new UsersPassword();
        usersPassword.setUsers(user);
        //usersPassword.setGrantKey(users.getPassword());
        usersPassword.setPassword(this.passwordEncoder.encode(users.getPassword()));
        usersPassword.setActive(true);
        usersPasswordRepository.save(usersPassword);

        return new Response<>(converter.convertToDto(user));
    }

    @Override
    public Response<Boolean> resetPassword(String id, String password, String confirmPassword) {

        try {
            Optional<UsersPassword> usersPasswordOptional = usersPasswordRepository.findByUsersIdAndActive(id, Boolean.TRUE);

            if (!usersPasswordOptional.isPresent()) {
                return new Response<>(false).withMessage(new Message(MessageType.ERROR, "", String.format("No record found for user [%s]", id)));
            }

            // check password is not-null and minimum 8 chars.
            if (StringUtils.isEmpty(password) || password.length() < 8 || !StringUtils.equals(password, confirmPassword)) {
                return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Password should be minimum [%s] chars", 8)));
            }

            // UsersPassword
            UsersPassword usersPassword = usersPasswordOptional.get();
            usersPassword.setPassword(this.passwordEncoder.encode(password));
            usersPassword.setActive(true);
            usersPasswordRepository.save(usersPassword);

            return new Response<>(true);
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
            return new Response<>(false).withMessage(new Message(MessageType.ERROR, "", String.format("Error -> [%s]", e.getLocalizedMessage())));
        }
    }

    @Override
    public void isUserEntitled(String s, String user) {
        // TODO
    }

}
