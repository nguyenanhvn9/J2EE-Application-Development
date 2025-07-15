package com.example.demo.model;

import java.util.List;

public class Book {
    private int id;
    private String title;
    private List<String> authors;
    private int downloadCount;

    public Book() {}

    public Book(int id, String title, List<String> authors, int downloadCount) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.downloadCount = downloadCount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> authors) { this.authors = authors; }

    public int getDownloadCount() { return downloadCount; }
    public void setDownloadCount(int downloadCount) { this.downloadCount = downloadCount; }

    // ✅ Thêm phương thức này để tránh lỗi Thymeleaf
    public String getAuthorsString() {
        return authors != null ? String.join(", ", authors) : "";
    }
}
