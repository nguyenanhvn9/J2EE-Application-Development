package com.example.BaitapJ2EE.QuanLySach;

public class Book {
    private int id;
    private String title;
    private String authors;
    private String description;
    private String coverImageUrl;

    public Book() {}

    public Book(int id, String title, String authors, String description, String coverImageUrl) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthors() { return authors; }
    public void setAuthors(String authors) { this.authors = authors; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
} 