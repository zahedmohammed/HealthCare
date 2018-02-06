package com.fxlabs.fxt.rest.base;

import com.fxlabs.fxt.dto.users.Org;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Intesar Shannan Mohammed
 */
public class SecurityUtil {

    public static String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((FxUserPrinciple) authentication.getPrincipal()).getUsername();
    }

    public static Org getOrg() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((FxUserPrinciple) authentication.getPrincipal()).getOrg();
    }
}
