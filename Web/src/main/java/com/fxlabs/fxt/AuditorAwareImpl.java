package com.fxlabs.fxt;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private Optional<String> auditor = Optional.empty();

    /**
     * @param auditor the auditor to set
     */
    public void setAuditor(String auditor) {
        this.auditor = Optional.of(auditor);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
     */
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        Optional<String> username = Optional.of((((User) authentication.getPrincipal()).getUsername()));

        return username;
    }
}