package com.hutech.cos141_demo.BaiTH03_04.dto;

public class UserDTO {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    // Có thể bổ sung các trường khác nếu cần (address, company, ...)

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
} 