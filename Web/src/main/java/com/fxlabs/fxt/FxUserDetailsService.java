package com.fxlabs.fxt;

import com.fxlabs.fxt.converters.users.OrgConverter;
import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.converters.users.UsersPasswordConverter;
import com.fxlabs.fxt.dao.entity.users.Org;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersPasswordRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.dto.users.UsersPassword;
import com.fxlabs.fxt.rest.base.FxUserPrinciple;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
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
        Optional<com.fxlabs.fxt.dao.entity.users.Users> usersOptional = usersRepository.findByEmail(email);
        if (!usersOptional.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        Optional<com.fxlabs.fxt.dao.entity.users.UsersPassword> usersPasswordOptional = this.usersPasswordRepository.findByUsersEmailAndActive(email, true);
        if (!usersPasswordOptional.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        com.fxlabs.fxt.dao.entity.users.Users users = usersOptional.get();
        com.fxlabs.fxt.dao.entity.users.UsersPassword usersPassword = usersPasswordOptional.get();

        com.fxlabs.fxt.dto.users.Org org = null;
        if (StringUtils.isNotEmpty(orgName)) {
            Optional<OrgUsers> orgOptional = orgUsersRepository.findByUsersIdAndStatusAndOrgNameAndOrgRole(users.getId(), OrgUserStatus.ACTIVE, orgName, OrgRole.ADMIN);
            if (orgOptional.isPresent()) {
                org = orgConverter.convertToDto(orgOptional.get().getOrg());
            }
        }

        return new FxUserPrinciple(usersConverter.convertToDto(usersOptional.get()), usersPasswordConverter.convertToDto(usersPassword), org);
    }
}
