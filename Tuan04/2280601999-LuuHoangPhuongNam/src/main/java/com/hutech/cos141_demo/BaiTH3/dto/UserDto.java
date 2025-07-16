package com.hutech.cos141_demo.BaiTH3.dto;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    // Có thể bổ sung các trường khác nếu cần (address, company, ...)
} 