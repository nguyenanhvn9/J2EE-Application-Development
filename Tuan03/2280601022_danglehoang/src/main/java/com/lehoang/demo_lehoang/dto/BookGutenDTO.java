package com.lehoang.demo_lehoang.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BookGutenDTO {
    private int id;
    private String title;
    private List<AuthorDTO> authors;

    @JsonProperty("id")
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @JsonProperty("title")
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @JsonProperty("authors")
    public List<AuthorDTO> getAuthors() { return authors; }
    public void setAuthors(List<AuthorDTO> authors) { this.authors = authors; }

    public static class AuthorDTO {
        private String name;
        @JsonProperty("name")
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
} 