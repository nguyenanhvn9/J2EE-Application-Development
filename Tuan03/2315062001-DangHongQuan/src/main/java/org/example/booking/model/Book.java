package org.example.booking.model;

import java.util.List;

public class Book {
    private Long id;
    private String title;
    private String author;
    private int downloadCount;
    private String language;
    private List<String> subjects;
    private String imageUrl;
    private String summary;

    // Constructor
    public Book(Long id, String title, String author, int downloadCount, String language,
                List<String> subjects, String imageUrl, String summary) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.downloadCount = downloadCount;
        this.language = language;
        this.subjects = subjects;
        this.imageUrl = imageUrl;
        this.summary = summary;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getDownloadCount() { return downloadCount; }
    public String getLanguage() { return language; }
    public List<String> getSubjects() { return subjects; }
    public String getImageUrl() { return imageUrl; }
    public String getSummary() { return summary; }

    // Setters (nếu cần cập nhật dữ liệu sau khi tạo)
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setDownloadCount(int downloadCount) { this.downloadCount = downloadCount; }
    public void setLanguage(String language) { this.language = language; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setSummary(String summary) { this.summary = summary; }
}
