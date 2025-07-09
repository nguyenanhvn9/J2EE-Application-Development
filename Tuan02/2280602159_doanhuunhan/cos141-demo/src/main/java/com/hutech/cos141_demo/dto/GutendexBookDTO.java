package com.hutech.cos141_demo.dto;

import java.util.List;

public class GutendexBookDTO {
    private int id;
    private String title;
    private List<AuthorDTO> authors;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public static class AuthorDTO {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
