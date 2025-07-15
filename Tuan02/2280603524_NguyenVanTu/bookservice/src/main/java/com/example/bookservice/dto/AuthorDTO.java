package com.example.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorDTO {
    private String name;
    
    @JsonProperty("birth_year")
    private Integer birthYear;
    
    @JsonProperty("death_year")
    private Integer deathYear;

    // Constructors
    public AuthorDTO() {}

    public AuthorDTO(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

    public Integer getDeathYear() { return deathYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }

    @Override
    public String toString() {
        return name;
    }
}
