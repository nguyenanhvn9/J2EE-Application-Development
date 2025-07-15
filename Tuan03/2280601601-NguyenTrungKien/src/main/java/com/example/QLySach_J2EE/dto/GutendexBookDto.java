package com.example.QLySach_J2EE.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GutendexBookDto {
    private int id;
    private String title;
    @JsonProperty("authors")
    private AuthorDto[] authors;

    @Data
    public static class AuthorDto {
        private String name;
    }
} 