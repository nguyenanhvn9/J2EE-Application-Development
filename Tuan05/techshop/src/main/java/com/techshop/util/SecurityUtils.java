package com.techshop.util;

import com.techshop.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**
     * Kiểm tra user hiện tại có role ADMIN không
     */
    public static boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Kiểm tra user hiện tại có role MANAGER hoặc ADMIN không
     */
    public static boolean isManagerOrAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MANAGER"));
    }

    /**
     * Kiểm tra user hiện tại có role LEADER, MANAGER hoặc ADMIN không
     */
    public static boolean isLeaderOrHigher() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") ||
                        a.getAuthority().equals("ROLE_MANAGER") ||
                        a.getAuthority().equals("ROLE_LEADER"));
    }

    /**
     * Lấy role hiện tại của user
     */
    public static String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().size() > 0) {
            String authority = auth.getAuthorities().iterator().next().getAuthority();
            return authority.replace("ROLE_", "");
        }
        return null;
    }
}