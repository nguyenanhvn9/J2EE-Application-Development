package com.hutech.cos141_demo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String name;
    private String username;
    private String email;
    private AddressDTO address;
    private String phone;
    private String website;
    private CompanyDTO company;
}
