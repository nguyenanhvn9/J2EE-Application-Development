package com.techshop.util;

import com.techshop.model.Role;
import java.util.Arrays;
import java.util.List;

public class RoleUtils {

    public static List<Role> getAllRoles() {
        return Arrays.asList(Role.values());
    }

    public static List<Role> getAdminRoles() {
        return Arrays.asList(Role.ADMIN, Role.MANAGER, Role.LEADER);
    }

    public static List<Role> getManagerRoles() {
        return Arrays.asList(Role.ADMIN, Role.MANAGER);
    }

    public static boolean isAdminRole(Role role) {
        return role == Role.ADMIN;
    }

    public static boolean isManagerRole(Role role) {
        return role == Role.MANAGER || role == Role.ADMIN;
    }

    public static boolean isLeaderRole(Role role) {
        return role == Role.LEADER || role == Role.MANAGER || role == Role.ADMIN;
    }

    public static String getRoleDisplayName(Role role) {
        switch (role) {
            case USER:
                return "Người dùng";
            case MANAGER:
                return "Quản lý";
            case LEADER:
                return "Trưởng nhóm";
            case ADMIN:
                return "Quản trị viên";
            default:
                return role.getValue();
        }
    }

    /**
     * Kiểm tra role có thể quản lý người dùng không (chỉ ADMIN)
     */
    public static boolean canManageUsers(Role role) {
        return role == Role.ADMIN;
    }

    /**
     * Kiểm tra role có thể quản lý danh mục không (chỉ ADMIN)
     */
    public static boolean canManageCategories(Role role) {
        return role == Role.ADMIN;
    }

    /**
     * Kiểm tra role có thể quản lý sản phẩm không (ADMIN, MANAGER)
     */
    public static boolean canManageProducts(Role role) {
        return role == Role.ADMIN || role == Role.MANAGER;
    }

    /**
     * Kiểm tra role có thể xóa sản phẩm không (chỉ ADMIN)
     */
    public static boolean canDeleteProducts(Role role) {
        return role == Role.ADMIN;
    }

    /**
     * Kiểm tra role có thể quản lý đơn hàng không (ADMIN, MANAGER, LEADER)
     */
    public static boolean canManageOrders(Role role) {
        return role == Role.ADMIN || role == Role.MANAGER || role == Role.LEADER;
    }

    /**
     * Kiểm tra role có thể quản lý voucher không (ADMIN, MANAGER, LEADER)
     */
    public static boolean canManageVouchers(Role role) {
        return role == Role.ADMIN || role == Role.MANAGER || role == Role.LEADER;
    }
}