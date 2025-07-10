package com.example.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BookDTO {
    public int id;
    public String title;

    @JsonProperty("authors")
    public List<AuthorDTO> authors;

    public static class AuthorDTO {
        public String name;
    }
}
