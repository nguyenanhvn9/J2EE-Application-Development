package com.lehoang.demo_lehoang.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorDto {
    public String name;
    @JsonProperty("birth_year")
    public Integer birthYear;
    @JsonProperty("death_year")
    public Integer deathYear;
} 