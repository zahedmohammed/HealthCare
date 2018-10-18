package com.fxlabs.issues.rest.base;

import com.fxlabs.issues.dto.users.Org;
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

        return ((FxUserPrinciple) authentication.getPrincipal()).getUserId();
    }

    public static Org getOrg() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((FxUserPrinciple) authentication.getPrincipal()).getOrg();
    }

    public static String getOrgId() {
        Org org = getOrg();
        return org != null ? org.getId() : null;
    }
}
