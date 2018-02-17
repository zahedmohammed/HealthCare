package com.fxlabs.fxt.rest.base;

import com.fxlabs.fxt.dto.users.Org;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.dto.users.UsersPassword;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public class FxUserPrinciple implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Users user;
    private final UsersPassword usersPassword;
    private final Org org;

    //
    public FxUserPrinciple(Users user, UsersPassword usersPassword, Org org) {
        this.user = user;
        this.usersPassword = usersPassword;
        this.org = org;
    }

    //
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return usersPassword.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : user.getPrivileges()) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    //
    public Users getUser() {
        return user;
    }

    public Org getOrg() {
        return org;
    }

    public String getUserId() {
        return user.getId();
    }

}

