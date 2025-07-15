package com.example.demo.dto;

import java.util.List;

public class BookDto {
    private int id;
    private String title;
    private List<AuthorDto> authors;
    private int download_count;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<AuthorDto> getAuthors() { return authors; }
    public void setAuthors(List<AuthorDto> authors) { this.authors = authors; }
    public int getDownload_count() { return download_count; }
    public void setDownload_count(int download_count) { this.download_count = download_count; }

    public static class AuthorDto {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
} 