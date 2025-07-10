package com.example.demo.dto;

import java.util.List;

public class BookDto {
    private String title;
    private List<AuthorDto> authors;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<AuthorDto> getAuthors() { return authors; }
    public void setAuthors(List<AuthorDto> authors) { this.authors = authors; }
}
