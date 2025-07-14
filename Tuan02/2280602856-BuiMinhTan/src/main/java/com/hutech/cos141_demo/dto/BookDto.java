package com.hutech.cos141_demo.dto;

import java.util.List;
import java.util.Map;

public class BookDto {
    private int id;
    private String title;
    private List<Map<String, String>> authors;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Map<String, String>> getAuthors() { return authors; }
    public void setAuthors(List<Map<String, String>> authors) { this.authors = authors; }
}
