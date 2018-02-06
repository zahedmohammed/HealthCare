package com.fxlabs.fxt;

import com.fxlabs.fxt.converters.users.OrgConverter;
import com.fxlabs.fxt.dao.entity.users.Org;
import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import com.fxlabs.fxt.dao.repository.jpa.OrgUsersRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.dto.users.UsersPassword;
import com.fxlabs.fxt.rest.base.FxUserPrinciple;
import com.fxlabs.fxt.services.users.UsersService;
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
    private UsersService usersService;

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
        final Response<Users> usersResponse = usersService.findByEmail(username);
        if (usersResponse.isErrors()) {
            throw new UsernameNotFoundException(username);
        }
        final Response<UsersPassword> usersPasswordResponse = usersService.findActivePassword(username);
        if (usersPasswordResponse.isErrors()) {
            throw new UsernameNotFoundException(usersPasswordResponse.getMessages().get(0).getValue());
        }

        com.fxlabs.fxt.dto.users.Org org = null;
        if (StringUtils.isNotEmpty(orgName)) {
            Optional<OrgUsers> orgOptional = orgUsersRepository.findByUsersIdAndStatusAndOrgNameAndOrgRole(usersResponse.getData().getId(), OrgUserStatus.ACTIVE, orgName, OrgRole.ADMIN);
            if (orgOptional.isPresent()) {
                org = orgConverter.convertToDto(orgOptional.get().getOrg());
            }
        }

        return new FxUserPrinciple(usersResponse.getData(), usersPasswordResponse.getData(), org);
    }
}
