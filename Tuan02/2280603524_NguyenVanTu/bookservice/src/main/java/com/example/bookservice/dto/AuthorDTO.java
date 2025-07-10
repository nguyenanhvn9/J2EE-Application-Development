package com.example.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorDTO {
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
