package com.garage.garage_backend.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
}
