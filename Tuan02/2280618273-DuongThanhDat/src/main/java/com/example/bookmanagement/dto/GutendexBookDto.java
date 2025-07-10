package com.example.bookmanagement.dto;

import java.util.List;
import java.util.Map;

public class GutendexBookDto {
    private Long id;
    private String title;
    private List<AuthorDto> authors;
    private List<String> subjects;
    private List<String> languages;
    private String copyright;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("download_count")
    private Integer downloadCount;

    private Map<String, String> formats;

    // Nested DTO for Author
    public static class AuthorDto {
        private String name;

        @JsonProperty("birth_year")
        private Integer birthYear;

        @JsonProperty("death_year")
        private Integer deathYear;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(Integer birthYear) {
            this.birthYear = birthYear;
        }

        public Integer getDeathYear() {
            return deathYear;
        }

        public void setDeathYear(Integer deathYear) {
            this.deathYear = deathYear;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Map<String, String> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, String> formats) {
        this.formats = formats;
    }
}
