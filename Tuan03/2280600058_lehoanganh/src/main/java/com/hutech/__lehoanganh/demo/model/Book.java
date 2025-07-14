package com.hutech.__lehoanganh.demo.model;

import java.util.List;

public class Book {
    private Integer id;
    private String title;
    private List<String> authors;
    private String coverUrl;

    public Book() {}

    public Book(Integer id, String title, List<String> authors, String coverUrl) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.coverUrl = coverUrl;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> authors) { this.authors = authors; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
} 