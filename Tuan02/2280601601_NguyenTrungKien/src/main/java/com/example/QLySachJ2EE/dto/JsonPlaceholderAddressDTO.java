package com.example.QLySachJ2EE.dto;

import lombok.Data;

@Data
public class  JsonPlaceholderAddressDTO {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private JsonPlaceholderGeoDTO geo;
} 