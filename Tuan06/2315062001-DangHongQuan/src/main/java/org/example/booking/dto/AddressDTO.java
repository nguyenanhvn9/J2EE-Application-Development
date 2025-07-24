package org.example.booking.dto;


public class AddressDTO {
    private Long id;
    private String addressLine;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    // Constructors
    public AddressDTO() {}
    public AddressDTO(Long id, String addressLine, String city, String state, String zipCode, String country) {
        this.id = id;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
// Getters and Setters
    // ...
}
