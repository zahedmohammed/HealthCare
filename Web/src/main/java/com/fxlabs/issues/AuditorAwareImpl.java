package com.fxlabs.issues;

import com.fxlabs.issues.rest.base.FxUserPrinciple;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
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
        Optional<String> username = Optional.empty();
        if (authentication.getPrincipal() instanceof FxUserPrinciple) {
            username = Optional.ofNullable(((FxUserPrinciple) authentication.getPrincipal()).getUserId());
        } else if (authentication.getPrincipal() instanceof String) {
            username = Optional.ofNullable(authentication.getPrincipal().toString());
        }


        return username;
    }
}
