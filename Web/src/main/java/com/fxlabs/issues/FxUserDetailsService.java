package com.fxlabs.issues;

import com.fxlabs.issues.converters.users.OrgConverter;
import com.fxlabs.issues.converters.users.UsersConverter;
import com.fxlabs.issues.converters.users.UsersPasswordConverter;
import com.fxlabs.issues.dao.entity.users.*;
import com.fxlabs.issues.dao.entity.users.OrgUsers;
import com.fxlabs.issues.dao.repository.jpa.AccessKeyRepository;
import com.fxlabs.issues.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.issues.dao.repository.jpa.UsersPasswordRepository;
import com.fxlabs.issues.dao.repository.jpa.UsersRepository;
import com.fxlabs.issues.rest.base.FxUserPrinciple;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Service
public class FxUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersConverter usersConverter;

    @Autowired
    private UsersPasswordRepository usersPasswordRepository;

    @Autowired
    private UsersPasswordConverter usersPasswordConverter;

    @Autowired
    private OrgUsersRepository orgUsersRepository;

    @Autowired
    private AccessKeyRepository accessKeyRepository;

    @Autowired
    private OrgConverter orgConverter;

    public FxUserDetailsService() {
        super();
    }

    // API

    @Override
    public UserDetails loadUserByUsername(final String username) {
        String orgName = null;
        String email = username;

        if (StringUtils.contains(username, "//")) {
            String[] tokens = StringUtils.split(username, "//");
            orgName = tokens[0];
            email = tokens[1];
        }

        AccessKey accessKey = null;
        Optional<AccessKey> accessKeyOption = accessKeyRepository.findByAccessKeyAndExpirationAfter(username, new Date());
        if (accessKeyOption.isPresent()) {
            accessKey = accessKeyOption.get();
            email = accessKeyOption.get().getUsers().getEmail();
        }

        Optional<Users> usersOptional = usersRepository.findByEmail(email);
        if (!usersOptional.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        Optional<UsersPassword> usersPasswordOptional = this.usersPasswordRepository.findByUsersEmailAndActive(email, true);
        if (!usersPasswordOptional.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        Users users = usersOptional.get();
        UsersPassword usersPassword = usersPasswordOptional.get();

        com.fxlabs.issues.dto.users.Org org = null;
        List<String> privileges = null;
        if (StringUtils.isNotEmpty(orgName)) {
            Optional<OrgUsers> orgOptional = orgUsersRepository.findByUsersIdAndStatusAndOrgName(users.getId(), OrgUserStatus.ACTIVE, orgName);
            if (orgOptional.isPresent()) {
                org = orgConverter.convertToDto(orgOptional.get().getOrg());
                privileges = getRole(orgOptional.get().getOrgRole());
            }
        } else {
            List<OrgUsers> orgUsersList = orgUsersRepository.findByUsersIdAndStatus(users.getId(), OrgUserStatus.ACTIVE, PageRequest.of(0, 1, new Sort(Sort.Direction.DESC, "createdDate")));
            if (!CollectionUtils.isEmpty(orgUsersList)) {
                org = orgConverter.convertToDto(orgUsersList.get(0).getOrg());
                privileges = getRole(orgUsersList.get(0).getOrgRole());
            }
        }

        if (org == null || CollectionUtils.isEmpty(privileges)) {
            throw new UsernameNotFoundException(email);
        }

        if (accessKey != null) {
            return new FxUserPrinciple(usersConverter.convertToDto(usersOptional.get()), accessKey.getSecretKey(), org, privileges);
        }

        return new FxUserPrinciple(usersConverter.convertToDto(usersOptional.get()), usersPassword.getPassword(), org, privileges);
    }

    private static List<String> getRole(OrgRole orgRole) {
        switch (orgRole) {
            case USER:
                return USER_PRIVILEGES;
            case PROJECT_MANAGER:
                return PM_PRIVILEGES;
            case ADMIN:
                return ADMIN_PRIVILEGES;
            case ENTERPRISE_ADMIN:
                return ENTERPRISE_ADMIN_PRIVILEGES;
            default:
                return NO_PRIVILEGES;
        }
    }

    final static List<String> USER_PRIVILEGES = new ArrayList<>();
    final static List<String> PM_PRIVILEGES = new ArrayList<>();
    final static List<String> ADMIN_PRIVILEGES = new ArrayList<>();
    final static List<String> ENTERPRISE_ADMIN_PRIVILEGES = new ArrayList<>();

    final static List<String> NO_PRIVILEGES = new ArrayList<>();

    static {

        USER_PRIVILEGES.add("ROLE_USER");

        PM_PRIVILEGES.add("ROLE_USER");
        PM_PRIVILEGES.add("ROLE_PROJECT_MANAGER");

        ADMIN_PRIVILEGES.add("ROLE_USER");
        ADMIN_PRIVILEGES.add("ROLE_PROJECT_MANAGER");
        ADMIN_PRIVILEGES.add("ROLE_ADMIN");


        ENTERPRISE_ADMIN_PRIVILEGES.add("ROLE_USER");
        ENTERPRISE_ADMIN_PRIVILEGES.add("ROLE_PROJECT_MANAGER");
        ENTERPRISE_ADMIN_PRIVILEGES.add("ROLE_ADMIN");
        ENTERPRISE_ADMIN_PRIVILEGES.add("ROLE_ENTERPRISE_ADMIN");

    }
}
