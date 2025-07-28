package com.techshop.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class ThymeleafUtils {

    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return false;
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role));
    }

    public boolean hasAnyRole(String... roles) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return false;
        for (String role : roles) {
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role))) {
                return true;
            }
        }
        return false;
    }

    public String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null)
            return "USER";

        if (hasRole("ADMIN"))
            return "ADMIN";
        if (hasRole("MANAGER"))
            return "MANAGER";
        if (hasRole("LEADER"))
            return "LEADER";
        return "USER";
    }
}