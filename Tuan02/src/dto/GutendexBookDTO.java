package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GutendexBookDTO {
    private int id;
    private String title;

    @JsonProperty("authors")
    private List<AuthorDTO> authors;

    // Default constructor
    public GutendexBookDTO() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    // Nested AuthorDTO class
    public static class AuthorDTO {
        private String name;

        public AuthorDTO() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
