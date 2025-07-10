package com.hutech.cos141_demo.BaiTH01_02_04.model;
import java.util.List;

public class Book {
    private int id;
    private String title;
    private List<String> authors;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> authors) { this.authors = authors; }
} 