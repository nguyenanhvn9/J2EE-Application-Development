package com.hutech.__truonghoanghuy.model;

import java.util.List;

public class Book {
    private int id;
    private String title;
    private List<String> authorNames;

    public Book(int id, String title, List<String> authorNames) {
        this.id = id;
        this.title = title;
        this.authorNames = authorNames;
    }

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

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }
} 