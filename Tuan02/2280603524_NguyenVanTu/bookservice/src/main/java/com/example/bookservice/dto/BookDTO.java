package com.example.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

public class BookDTO {
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private List<String> authors;
    private List<String> subjects;
    private List<String> languages;
    
    @Positive(message = "Download count must be positive")
    private Integer downloadCount;
    
    private String copyright;
    private Integer gutenbergId;

    // Constructors
    public BookDTO() {}

    public BookDTO(String title, List<String> authors, List<String> subjects, 
                   List<String> languages, Integer downloadCount, String copyright) {
        this.title = title;
        this.authors = authors;
        this.subjects = subjects;
        this.languages = languages;
        this.downloadCount = downloadCount;
        this.copyright = copyright;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> authors) { this.authors = authors; }

    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }

    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }

    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }

    public String getCopyright() { return copyright; }
    public void setCopyright(String copyright) { this.copyright = copyright; }

    public Integer getGutenbergId() { return gutenbergId; }
    public void setGutenbergId(Integer gutenbergId) { this.gutenbergId = gutenbergId; }
}
