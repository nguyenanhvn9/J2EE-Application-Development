package com.example.j2ee.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Book {
    private Integer id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Author is required")
    private String author;
    
    private String description;
    private String downloadCount;
    private String languages;
    private String subjects;
    
    public Book() {}
    
    public Book(Integer id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    
    public Book(Integer id, String title, String author, String description, 
                String downloadCount, String languages, String subjects) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.downloadCount = downloadCount;
        this.languages = languages;
        this.subjects = subjects;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDownloadCount() {
        return downloadCount;
    }
    
    public void setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
    }
    
    public String getLanguages() {
        return languages;
    }
    
    public void setLanguages(String languages) {
        this.languages = languages;
    }
    
    public String getSubjects() {
        return subjects;
    }
    
    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", downloadCount='" + downloadCount + '\'' +
                ", languages='" + languages + '\'' +
                ", subjects='" + subjects + '\'' +
                '}';
    }
} 