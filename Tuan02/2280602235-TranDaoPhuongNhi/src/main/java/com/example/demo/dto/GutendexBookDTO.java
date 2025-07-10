package com.example.demo.dto;

import java.util.List;

public class GutendexBookDTO {
    private int id;
    private String title;
    private List<GutendexAuthorDTO> authors;

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

    public List<GutendexAuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<GutendexAuthorDTO> authors) {
        this.authors = authors;
    }
} 