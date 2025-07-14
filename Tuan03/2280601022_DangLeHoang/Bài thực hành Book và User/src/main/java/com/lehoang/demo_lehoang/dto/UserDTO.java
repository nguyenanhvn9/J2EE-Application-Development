package com.lehoang.demo_lehoang.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;

    @JsonProperty("id")
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    @JsonProperty("name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @JsonProperty("username")
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    @JsonProperty("email")
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    @JsonProperty("phone")
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
} 