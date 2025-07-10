package com.example.QLySachJ2EE.dto;

import lombok.Data;

@Data
public class JsonPlaceholderUserDTO {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private JsonPlaceholderAddressDTO address;
    private JsonPlaceholderCompanyDTO company;
} 