package com.fxlabs.issues.rest.base;

import com.fxlabs.issues.dto.users.Org;
import com.fxlabs.issues.dto.users.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public class FxUserPrinciple implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Users user;
    private final String usersPassword;
    private final Org org;
    private final List<String> privileges;

    //
    public FxUserPrinciple(Users user, String usersPassword, Org org, List<String> privileges) {
        this.user = user;
        this.usersPassword = usersPassword;
        this.org = org;
        this.privileges = privileges;
    }

    //
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return usersPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        /*for (final String privilege : user.getPrivileges()) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }*/

        privileges.forEach(p -> {
            authorities.add(new SimpleGrantedAuthority(p));
        });

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

