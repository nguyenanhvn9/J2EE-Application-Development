package org.example.booking.dto;

import java.util.List;
import java.util.Map;

public class BookDTO {
    private Long id;
    private String title;
    private List<AuthorDTO> authors;   // ⬅️ Quan trọng
    private int download_count;
    private List<String> languages;
    private List<String> subjects;
    private Map<String, String> formats;
    private List<String> summaries;

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public List<AuthorDTO> getAuthors() { return authors; } // ⬅️ Cần có
    public int getDownload_count() { return download_count; }
    public List<String> getLanguages() { return languages; }
    public List<String> getSubjects() { return subjects; }
    public Map<String, String> getFormats() { return formats; }
    public List<String> getSummaries() { return summaries; }

    // Setters (nếu cần)
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthors(List<AuthorDTO> authors) { this.authors = authors; }
    public void setDownload_count(int download_count) { this.download_count = download_count; }
    public void setLanguages(List<String> languages) { this.languages = languages; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }
    public void setFormats(Map<String, String> formats) { this.formats = formats; }
    public void setSummaries(List<String> summaries) { this.summaries = summaries; }
}
