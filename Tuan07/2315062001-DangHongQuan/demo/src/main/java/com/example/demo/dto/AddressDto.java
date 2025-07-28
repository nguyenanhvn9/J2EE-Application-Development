package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressDto {

    private Long id; // Add ID if you're using it for editing/updating

    @NotBlank(message = "Tên gợi nhớ không được để trống")
    @Size(max = 100, message = "Tên gợi nhớ không được vượt quá 100 ký tự")
    private String alias;

    @NotBlank(message = "Tên người nhận không được để trống")
    @Size(max = 255, message = "Tên người nhận không được vượt quá 255 ký tự")
    private String recipientName;

    @NotBlank(message = "Số điện thoại không được để trống")
// Chấp nhận 8 đến 10 chữ số sau 0 hoặc +84
    @Pattern(regexp = "^(0|\\+84)(\\d){8,10}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    @NotBlank(message = "Địa chỉ đường không được để trống")
    @Size(max = 255, message = "Địa chỉ đường không được vượt quá 255 ký tự")
    private String streetAddress;

    @NotBlank(message = "Phường/Xã không được để trống")
    @Size(max = 100, message = "Phường/Xã không được vượt quá 100 ký tự")
    private String ward;

    @NotBlank(message = "Quận/Huyện không được để trống")
    @Size(max = 100, message = "Quận/Huyện không được vượt quá 100 ký tự")
    private String district;

    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    @Size(max = 100, message = "Tỉnh/Thành phố không được vượt quá 100 ký tự")
    private String city;

    private boolean isDefault; // For the checkbox

    // Constructors (optional, but good practice)
    public AddressDto() {
    }

    public AddressDto(Long id, String alias, String recipientName, String phoneNumber, String streetAddress, String ward, String district, String city, boolean isDefault) {
        this.id = id;
        this.alias = alias;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.streetAddress = streetAddress;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.isDefault = isDefault;
    }

    // --- GETTERS AND SETTERS ---
    // Make sure these are present and correctly named!

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // For boolean 'isDefault', the getter can be 'isDefault()'
    // Sửa getter thành isDefault()
    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}