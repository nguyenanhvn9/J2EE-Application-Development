package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data // Lombok: Tự động tạo getters, setters, toString, equals, hashCode
public class UserProfileDto {
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 3, max = 100, message = "Họ và tên phải từ 3 đến 100 ký tự")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(min = 10, max = 20, message = "Số điện thoại phải từ 10 đến 20 ký tự")
    private String phone;
}