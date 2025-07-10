package com.example.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO {
    private int id;
    private String title;
    private List<AuthorDTO> authors;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public List<AuthorDTO> getAuthors() { return authors; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthors(List<AuthorDTO> authors) { this.authors = authors; }

    // Helper để lấy tên tác giả đầu tiên (nếu có)
    public String getAuthorName() {
        if (authors != null && !authors.isEmpty()) {
            return authors.get(0).getName();
        }
        return "Unknown";
    }
}
