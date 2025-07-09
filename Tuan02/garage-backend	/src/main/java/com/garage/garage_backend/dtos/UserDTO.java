package com.garage.garage_backend.dtos;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
}
