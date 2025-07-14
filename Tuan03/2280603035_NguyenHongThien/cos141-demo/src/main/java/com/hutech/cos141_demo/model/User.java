package com.hutech.cos141_demo.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    
    // Phương thức tạo User từ UserDTO
    public static User fromDTO(com.hutech.cos141_demo.dto.UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        return user;
    }
}
