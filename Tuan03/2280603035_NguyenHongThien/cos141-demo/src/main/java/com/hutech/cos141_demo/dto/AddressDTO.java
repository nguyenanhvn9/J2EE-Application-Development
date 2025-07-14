package com.hutech.cos141_demo.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoDTO geo;
}

@Data
class GeoDTO {
    private String lat;
    private String lng;
}

@Data
class CompanyDTO {
    private String name;
    private String catchPhrase;
    private String bs;
}
