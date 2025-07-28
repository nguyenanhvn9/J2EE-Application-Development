package com.techshop.model;

public enum Role {
    USER("USER"),
    MANAGER("MANAGER"),
    LEADER("LEADER"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role fromString(String text) {
        for (Role role : Role.values()) {
            if (role.value.equalsIgnoreCase(text)) {
                return role;
            }
        }
        return USER; // Default role
    }
}